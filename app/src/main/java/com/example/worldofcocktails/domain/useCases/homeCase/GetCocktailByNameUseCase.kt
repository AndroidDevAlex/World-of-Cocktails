package com.example.worldofcocktails.domain.useCases.homeCase

import com.example.worldofcocktails.data.repository.HomeRepository
import com.example.worldofcocktails.util.Cocktail
import javax.inject.Inject

class GetCocktailByNameUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend fun searchCocktailByName(query: String): Cocktail {
        return homeRepository.searchCocktailByName(query)
    }
}

