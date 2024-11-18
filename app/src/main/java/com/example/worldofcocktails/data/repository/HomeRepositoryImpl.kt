package com.example.worldofcocktails.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.database.local.CocktailDetailDao
import com.example.database.local.CocktailsDao
import com.example.worldofcocktails.data.api.ApiManager
import com.example.worldofcocktails.data.api.CocktailPagingSource
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Cocktail
import com.example.worldofcocktails.util.mapToDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    private val cocktailsDao: CocktailsDao,
    private val cocktailDetailDao: CocktailDetailDao
) : HomeRepository{

    override fun getPagedCocktails(): Flow<PagingData<CocktailEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CocktailPagingSource(apiManager) }
        ).flow
    }

    override suspend fun saveCocktail(cocktail: CocktailEntity) {
        cocktailsDao.insertDrink(cocktail.mapToDB())

        cocktail.detail?.let {
            val cocktailDetailDB = it.mapToDB(cocktail.idDrink)
            cocktailDetailDao.insertCocktailDetail(cocktailDetailDB)
        }
    }


    override suspend fun isCocktailSaved(id: String): Boolean {
        return cocktailsDao.isCocktailSaved(id)
    }

    override suspend fun searchCocktailByName(query: String): Cocktail {
        return apiManager.searchCocktailByName(query)
    }
}