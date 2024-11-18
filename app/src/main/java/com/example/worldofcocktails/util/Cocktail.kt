package com.example.worldofcocktails.util

import com.example.worldofcocktails.entityUi.CocktailEntity

sealed class Cocktail {
    data class Success(val cocktail: CocktailEntity): Cocktail()
    data object Empty: Cocktail()
    data class Error(val exception: Throwable) : Cocktail()
}