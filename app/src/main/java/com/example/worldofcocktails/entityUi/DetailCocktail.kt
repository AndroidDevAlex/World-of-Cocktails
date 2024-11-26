package com.example.worldofcocktails.entityUi

data class DetailCocktail(
    val instructions: String,
    val glassType: String,
    val ingredients: List<DrinkRecipe>
)
