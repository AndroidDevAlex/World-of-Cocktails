package com.example.worldofcocktails.domain.useCases.libraryCase

import com.example.worldofcocktails.data.repository.LibraryRepository
import com.example.worldofcocktails.entityUi.CocktailEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCocktailsByDBUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {

    fun getOllCocktails(): Flow<List<CocktailEntity>>{
        return libraryRepository.getOllSavedCocktails()
    }
}