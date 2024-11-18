package com.example.worldofcocktails.domain.useCases.detailCase

import com.example.worldofcocktails.data.repository.DetailRepository
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Request
import com.example.worldofcocktails.util.toRequest
import javax.inject.Inject

class GetCocktailDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {

    suspend fun getCocktailsFromApi(id: String): Request<CocktailEntity> {
        return detailRepository.getFullCocktailDetailFromApi(id).toRequest()
    }

    suspend fun getCocktailFromDB(id: String): CocktailEntity {
        return detailRepository.getFullCocktailDetailFromDB(id)
    }
}