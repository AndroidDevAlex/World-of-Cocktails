package com.example.worldofcocktails.data.api

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
}