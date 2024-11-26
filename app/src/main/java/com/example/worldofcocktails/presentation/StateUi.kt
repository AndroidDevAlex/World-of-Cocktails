package com.example.worldofcocktails.presentation

sealed class StateUI<out T> {
    data object Loading : StateUI<Nothing>()

    data object Empty : StateUI<Nothing>()

    data class Success<T>(val data: T) : StateUI<T>()

    data class Error(val exception: Throwable) : StateUI<Nothing>()
}
