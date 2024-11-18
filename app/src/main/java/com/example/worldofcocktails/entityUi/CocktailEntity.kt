package com.example.worldofcocktails.entityUi

data class CocktailEntity(
    val idDrink: String,
    val name: String,
    val image: String?,
    val category: String,
    val alcoholType: String,
    val detail: DetailCocktail?,
    val isBookmarked: Boolean
)