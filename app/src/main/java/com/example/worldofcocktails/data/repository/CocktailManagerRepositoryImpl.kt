package com.example.worldofcocktails.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.database.local.CocktailLocalDataSource
import com.example.worldofcocktails.data.api.ApiManager
import com.example.worldofcocktails.data.api.CocktailPagingSource
import com.example.worldofcocktails.data.api.Resource
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Cocktail
import com.example.worldofcocktails.data.mapFromDBToUiShort
import com.example.worldofcocktails.data.mapToDB
import com.example.worldofcocktails.data.toCocktailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CocktailManagerRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    private val localDataSource: CocktailLocalDataSource
) : CocktailManagerRepository {

    override suspend fun getFullCocktailDetailFromApi(id: String): Resource<CocktailEntity> {
        return apiManager.getCocktailDetail(id)
    }

    override suspend fun getFullCocktailDetailFromDB(cocktailId: String): CocktailEntity {
        val result = localDataSource.getCocktailDetail(cocktailId)
        return result.toCocktailEntity()
    }

    override fun getPagedCocktails(): Flow<PagingData<CocktailEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CocktailPagingSource(apiManager) }
        ).flow.map { pagingData ->
            pagingData.map {cocktail ->

                val isSaved = localDataSource.isCocktailSaved(cocktail.idDrink)
                cocktail.copy(isBookmarked = isSaved)
            }
        }
    }

    override suspend fun saveCocktail(cocktail: CocktailEntity) {

        val cocktailShortDB = cocktail.mapToDB()
        val cocktailDetailDB = cocktail.detail?.mapToDB(cocktail.idDrink)

        localDataSource.saveCocktail(cocktailShortDB, cocktailDetailDB)
    }

    override suspend fun isCocktailSaved(id: String): Boolean {
        return localDataSource.isCocktailSaved(id)
    }

    override suspend fun searchCocktailByName(query: String): Cocktail {
        return apiManager.searchCocktailByName(query)
    }

    override fun getOllSavedCocktails(): Flow<List<CocktailEntity>> {
        return localDataSource.getAllSavedCocktails()
            .map { dbCocktails ->
                dbCocktails.map { it.mapFromDBToUiShort() }
            }
    }

    override suspend fun deleteCocktail(id: String) {
        localDataSource.deleteCocktailById(id)
    }
}