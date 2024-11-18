package com.example.worldofcocktails.domain.useCases.homeCase

import androidx.paging.PagingData
import com.example.worldofcocktails.data.repository.HomeRepository
import com.example.worldofcocktails.entityUi.CocktailEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCocktailsByApiUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    fun getCocktails(): Flow<PagingData<CocktailEntity>>{
        return homeRepository.getPagedCocktails()
    }
}