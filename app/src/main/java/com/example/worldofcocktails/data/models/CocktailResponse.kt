package com.example.worldofcocktails.data.models

import com.google.gson.annotations.SerializedName

data class CocktailResponse(
    @SerializedName("drinks")
    val drinks: List<Drink> = emptyList()
)