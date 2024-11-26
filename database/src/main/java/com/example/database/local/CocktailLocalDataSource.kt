package com.example.database.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CocktailLocalDataSource @Inject constructor(
    private val cocktailsDao: CocktailsDao,
    private val cocktailDetailDao: CocktailDetailDao
) {

    fun getAllSavedCocktails(): Flow<List<CocktailDB>> {
        return cocktailsDao.getOllCocktail()
    }

    suspend fun deleteCocktailById(id: String) {
        cocktailsDao.deleteDrinkById(id)
    }

    suspend fun isCocktailSaved(id: String): Boolean {
        return cocktailsDao.isCocktailSaved(id)
    }

    suspend fun saveCocktail(cocktail: CocktailDB, detail: CocktailDetailDB?) {
        cocktailsDao.insertDrink(cocktail)
        detail?.let {
            cocktailDetailDao.insertCocktailDetail(it)
        }
    }

    suspend fun getCocktailDetail(idDrink: String): EntitiesTuple {
        return cocktailDetailDao.getFullInformation(idDrink)
    }
}