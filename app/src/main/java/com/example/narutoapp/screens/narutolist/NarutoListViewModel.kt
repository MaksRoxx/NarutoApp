package com.example.narutoapp.screens.narutolist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.data.remote.responses.Character
import com.example.narutoapp.repository.NarutoRepository
import com.example.narutoapp.util.Constants.PAGE_SIZE
import com.example.narutoapp.util.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NarutoListViewModel @Inject constructor(
    private val repository: NarutoRepository
) : ViewModel() {
    private var currentPage = 1
    private var totalCharacters = 0

    var charactersList = mutableStateListOf<Character>()

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var cachedCharacterList = mutableStateListOf<Character>()
    private var isSearchingStarted = true
    private var isSearching = mutableStateOf(false)

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        if (charactersList.size < totalCharacters || charactersList.size == 0) {
            viewModelScope.launch {
                val result = repository.getALlCharacter(page = currentPage, limit = PAGE_SIZE)
                totalCharacters = result.data?.totalCharacters ?: 0
                when (result) {
                    is Resourse.Error -> {
                        loadError.value = result.message!!
                        isLoading.value = false
                    }

                    is Resourse.Loading -> {

                    }

                    is Resourse.Success -> {
                        loadError.value = ""
                        isLoading.value = false
                        totalCharacters = result.data!!.totalCharacters
                        charactersList.addAll(result.data.characters)
                        currentPage++
                    }
                }
            }
        }
    }

    fun searchCharacters(query: String) {
        val listToSearch = if (isSearchingStarted) {
            charactersList
        } else {
            cachedCharacterList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                charactersList.clear()
                loadCharacters()
                isSearching.value = false
                isSearchingStarted = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true) ||
                        it.id.toString() == query.trim()
            }
            if (isSearchingStarted) {
                cachedCharacterList = charactersList
                isSearchingStarted = false
            }
            charactersList.clear()
            charactersList.addAll(results)
            isSearching.value = true
        }
    }
}