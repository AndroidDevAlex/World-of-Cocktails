package com.example.worldofcocktails.util

import com.example.worldofcocktails.data.api.Resource

fun <T> Resource<T>.toRequest(): Request<T> {
    return when (this) {
        is Resource.Success -> Request.Success(this.data)
        is Resource.Error -> Request.Error(this.exception)
    }
}