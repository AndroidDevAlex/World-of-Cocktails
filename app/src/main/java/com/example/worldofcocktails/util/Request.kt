package com.example.worldofcocktails.util

sealed class Request<out T> {
    data class Success<T>(val data: T) : Request<T>()
    data class Error(val exception: Throwable) : Request<Nothing>()
    data object Loading : Request<Nothing>()
}