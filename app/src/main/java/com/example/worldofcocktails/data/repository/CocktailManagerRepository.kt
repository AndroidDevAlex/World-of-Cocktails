package com.example.worldofcocktails.data.repository

import androidx.paging.PagingData
import com.example.worldofcocktails.data.api.Resource
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailManagerRepository {

    fun getPagedCocktails(): Flow<PagingData<CocktailEntity>>

    suspend fun saveCocktail(cocktail: CocktailEntity)

    suspend fun isCocktailSaved(id: String): Boolean

    suspend fun searchCocktailByName(query: String): Cocktail

    suspend fun getFullCocktailDetailFromApi(id: String): Resource<CocktailEntity>

    suspend fun getFullCocktailDetailFromDB(cocktailId: String): CocktailEntity

    fun getOllSavedCocktails(): Flow<List<CocktailEntity>>

    suspend fun deleteCocktail(id: String)
}