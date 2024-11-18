package com.example.worldofcocktails.data.repository

import com.example.database.local.CocktailDetailDao
import com.example.worldofcocktails.data.api.ApiManager
import com.example.worldofcocktails.data.api.Resource
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.toCocktailEntity
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    private val cocktailDetailDao: CocktailDetailDao
): DetailRepository{

    override suspend fun getFullCocktailDetailFromApi(id: String): Resource<CocktailEntity> {
        return apiManager.getFullCocktailDetailById(id)
    }

    override suspend fun getFullCocktailDetailFromDB(cocktailId: String): CocktailEntity {
        val result = cocktailDetailDao.getFullInformation(cocktailId)
        return result.toCocktailEntity()
    }
}