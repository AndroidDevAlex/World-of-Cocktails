package com.example.worldofcocktails.util

import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.presentation.StateUI

fun <T> Request<T>.toState(): StateUI<T> {
    return when (this) {
        is Request.Loading -> StateUI.Loading
        is Request.Error -> StateUI.Error(exception = this.exception)
        is Request.Success -> StateUI.Success(data = this.data)
    }
}

fun CocktailEntity.toState(): StateUI<CocktailEntity> {
    return StateUI.Success(this)
}
