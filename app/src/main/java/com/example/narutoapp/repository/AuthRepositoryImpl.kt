package com.example.narutoapp.repository

import com.example.narutoapp.data.AuthRepository
import com.example.narutoapp.util.Res
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Res<FirebaseUser> {
        return try {
            val  result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Res.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Res.Failure(e)
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Res<FirebaseUser> {
        return try {
            val  result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            Res.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Res.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}