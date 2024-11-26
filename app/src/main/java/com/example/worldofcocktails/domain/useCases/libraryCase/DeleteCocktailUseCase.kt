package com.example.worldofcocktails.domain.useCases.libraryCase

import com.example.worldofcocktails.data.repository.CocktailManagerRepository
import javax.inject.Inject

class DeleteCocktailUseCase @Inject constructor(
    private val repository: CocktailManagerRepository
) {
    suspend fun deleteCocktail(id: String) {
        repository.deleteCocktail(id)
    }
}