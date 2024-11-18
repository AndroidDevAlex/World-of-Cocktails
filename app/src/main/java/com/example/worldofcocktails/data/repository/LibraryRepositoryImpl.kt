package com.example.worldofcocktails.data.repository

import com.example.database.local.CocktailsDao
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.mapFromDBToUiShort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val cocktailsDao: CocktailsDao
): LibraryRepository {

    override fun getOllSavedCocktails(): Flow<List<CocktailEntity>> {
        return cocktailsDao.getOllCocktail()
            .map { dbCocktails ->
                dbCocktails.map { it.mapFromDBToUiShort() }
            }
    }

    override suspend fun deleteCocktail(id: String) {
        cocktailsDao.deleteDrinkById(id)
    }
}