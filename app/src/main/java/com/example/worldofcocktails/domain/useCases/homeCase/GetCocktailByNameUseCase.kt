package com.example.worldofcocktails.domain.useCases.homeCase

import com.example.worldofcocktails.data.repository.CocktailManagerRepository
import com.example.worldofcocktails.util.Cocktail
import javax.inject.Inject

class GetCocktailByNameUseCase @Inject constructor(
    private val repository: CocktailManagerRepository
) {

    suspend fun searchCocktailByName(query: String): Cocktail {
        return repository.searchCocktailByName(query)
    }
}

