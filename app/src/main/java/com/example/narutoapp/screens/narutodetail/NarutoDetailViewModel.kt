package com.example.narutoapp.screens.narutodetail

import androidx.lifecycle.ViewModel
import com.example.narutoapp.data.remote.responses.Character
import com.example.narutoapp.repository.NarutoRepository
import com.example.narutoapp.util.Resourse
import javax.inject.Inject

class NarutoDetailViewModel @Inject constructor(
    private val repository: NarutoRepository
): ViewModel() {
    suspend fun getCharacterInfo(id: String): Resourse<Character> {
        return repository.getCharacterById(id.toInt())
    }
}