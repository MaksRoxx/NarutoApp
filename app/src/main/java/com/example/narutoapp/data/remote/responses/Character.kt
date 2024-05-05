package com.example.narutoapp.data.remote.responses


data class Character(
    val id: Int,
    val name: String,
    val images: List<String>,
    val debut: Debut,
    // val personal: Personal,
    val family: Family,
    val jutsu: List<String>,
    val natureType: List<String>
)