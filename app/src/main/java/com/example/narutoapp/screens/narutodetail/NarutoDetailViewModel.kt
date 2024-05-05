package com.example.narutoapp.screens.narutodetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.narutoapp.data.remote.responses.Character
import com.example.narutoapp.repository.NarutoRepository
import com.example.narutoapp.util.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NarutoDetailViewModel @Inject constructor(
    private val repository: NarutoRepository
): ViewModel() {
    suspend fun getCharacterInfo(id: String): Resourse<Character> {
        return repository.getCharacterById(id.toInt())
    }
}