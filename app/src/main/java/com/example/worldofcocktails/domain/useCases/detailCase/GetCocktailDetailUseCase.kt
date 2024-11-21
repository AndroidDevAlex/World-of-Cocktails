package com.example.worldofcocktails.domain.useCases.detailCase

import com.example.worldofcocktails.data.repository.CocktailManagerRepository
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Request
import com.example.worldofcocktails.util.toRequest
import javax.inject.Inject

class GetCocktailDetailUseCase @Inject constructor(
    private val repository: CocktailManagerRepository
) {

    suspend fun getCocktailFromApi(id: String): Request<CocktailEntity> {
        return repository.getFullCocktailDetailFromApi(id).toRequest()
    }

    suspend fun getCocktailFromDB(id: String): CocktailEntity {
        return repository.getFullCocktailDetailFromDB(id)
    }
}