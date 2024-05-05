package com.example.narutoapp.util

sealed class Res<out R> {
    data class Success<out R>(val result: R): Res<R>()
    data class Failure(val exception: Exception): Res<Nothing>()
    object Loading: Res<Nothing>()
}