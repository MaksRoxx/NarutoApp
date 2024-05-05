package com.example.narutoapp.data

import com.example.narutoapp.util.Res
import com.google.firebase.auth.FirebaseUser

interface AuthRepository{
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Res<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Res<FirebaseUser>
    fun logout()
}