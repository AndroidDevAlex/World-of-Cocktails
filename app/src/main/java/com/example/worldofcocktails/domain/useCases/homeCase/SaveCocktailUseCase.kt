package com.example.worldofcocktails.domain.useCases.homeCase

import com.example.worldofcocktails.data.repository.HomeRepository
import com.example.worldofcocktails.entityUi.CocktailEntity
import javax.inject.Inject

class SaveCocktailUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend fun saveCocktail(cocktail: CocktailEntity){
        homeRepository.saveCocktail(cocktail)
    }

    suspend fun isCocktailSaved(id: String): Boolean{
        return homeRepository.isCocktailSaved(id)
    }
}