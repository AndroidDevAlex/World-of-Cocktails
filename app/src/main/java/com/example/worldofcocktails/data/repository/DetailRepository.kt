package com.example.worldofcocktails.data.repository

import com.example.worldofcocktails.data.api.Resource
import com.example.worldofcocktails.entityUi.CocktailEntity

interface DetailRepository {

   suspend fun getFullCocktailDetailFromApi(id: String): Resource<CocktailEntity>

   suspend fun getFullCocktailDetailFromDB(cocktailId: String): CocktailEntity
}