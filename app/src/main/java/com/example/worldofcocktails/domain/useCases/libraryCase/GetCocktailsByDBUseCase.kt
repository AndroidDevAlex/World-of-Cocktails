package com.example.worldofcocktails.domain.useCases.libraryCase

import com.example.worldofcocktails.data.repository.CocktailManagerRepository
import com.example.worldofcocktails.entityUi.CocktailEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCocktailsByDBUseCase @Inject constructor(
    private val repository: CocktailManagerRepository
) {

    fun getOllCocktails(): Flow<List<CocktailEntity>>{
        return repository.getOllSavedCocktails()
    }
}