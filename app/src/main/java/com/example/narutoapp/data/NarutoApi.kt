package com.example.narutoapp.data

import com.example.narutoapp.data.models.ListEntry
import com.example.narutoapp.data.remote.responses.Character
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NarutoApi {

    @Headers("accept: application/json")
    @GET("character")
    suspend fun getAllCharacter(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ListEntry

    @GET("character/search")
    suspend fun searchByName(
        @Query("name") name: String
    ): Character

    @GET("character/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Character
}