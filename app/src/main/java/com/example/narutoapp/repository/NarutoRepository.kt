package com.example.narutoapp.repository

import com.example.narutoapp.data.NarutoApi
import com.example.narutoapp.data.models.ListEntry
import com.example.narutoapp.data.remote.responses.Character
import com.example.narutoapp.util.Resourse
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class NarutoRepository @Inject constructor(
    private val api: NarutoApi
) {
    suspend fun getALlCharacter(page: Int, limit: Int): Resourse<ListEntry> {
        val response = try {
            api.getAllCharacter(page, limit)
        } catch (e: Exception) {
            return Resourse.Error("An unknown error occured.")
        }
        return Resourse.Success(response)
    }

    suspend fun getCharacterById(id: Int): Resourse<Character> {
        val response = try {
            api.getById(id)
        } catch (e: Exception) {
            return Resourse.Error("An unknown error occured.")
        }
        return Resourse.Success(response)
    }

    suspend fun searchByName(name: String): Resourse<Character> {
        val response = try {
            api.searchByName(name)
        } catch (e: Exception) {
            return Resourse.Error("An unknown error occured.")
        }
        return Resourse.Success(response)
    }
}