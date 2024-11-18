package com.example.worldofcocktails.domain.useCases.libraryCase

import com.example.worldofcocktails.data.repository.LibraryRepository
import javax.inject.Inject

class DeleteCocktailUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    suspend fun deleteCocktail(id: String) {
        libraryRepository.deleteCocktail(id)
    }
}