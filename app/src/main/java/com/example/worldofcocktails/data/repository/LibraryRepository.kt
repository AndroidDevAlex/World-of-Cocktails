package com.example.worldofcocktails.data.repository

import com.example.worldofcocktails.entityUi.CocktailEntity
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getOllSavedCocktails(): Flow<List<CocktailEntity>>

    suspend fun deleteCocktail(id: String)
}