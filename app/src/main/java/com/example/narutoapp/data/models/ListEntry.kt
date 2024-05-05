package com.example.narutoapp.data.models

import com.example.narutoapp.data.remote.responses.Character

data class ListEntry(
    val characters: List<Character>,
    val currentPage: Int,
    val pageSize: Int,
    val totalCharacters: Int
)