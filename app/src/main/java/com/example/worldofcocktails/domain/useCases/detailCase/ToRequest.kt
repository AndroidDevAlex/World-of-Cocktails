package com.example.worldofcocktails.domain.useCases.detailCase

import com.example.worldofcocktails.data.api.Resource
import com.example.worldofcocktails.util.Request

fun <T> Resource<T>.toRequest(): Request<T> {
    return when (this) {
        is Resource.Success -> Request.Success(this.data)
        is Resource.Error -> Request.Error(this.exception)
    }
}