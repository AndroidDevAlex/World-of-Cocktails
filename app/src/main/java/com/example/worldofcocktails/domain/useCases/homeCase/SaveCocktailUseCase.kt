package com.example.worldofcocktails.domain.useCases.homeCase

import com.example.worldofcocktails.data.repository.CocktailManagerRepository
import com.example.worldofcocktails.entityUi.CocktailEntity
import javax.inject.Inject

class SaveCocktailUseCase @Inject constructor(
    private val repository: CocktailManagerRepository
) {

    suspend fun saveCocktail(cocktail: CocktailEntity){
        repository.saveCocktail(cocktail)
    }

    suspend fun isCocktailSaved(id: String): Boolean{
        return repository.isCocktailSaved(id)
    }
}