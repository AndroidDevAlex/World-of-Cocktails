package com.example.worldofcocktails.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.worldofcocktails.entityUi.CocktailEntity

class CocktailPagingSource(
    private val apiManager: ApiManager
) : PagingSource<Char, CocktailEntity>() {

    override suspend fun load(params: LoadParams<Char>): LoadResult<Char, CocktailEntity> {
        val currentLetter = params.key ?: 'a'

        return when (val response = apiManager.getListCocktails(currentLetter.toString())) {
            is Resource.Success -> {
                val nextKey = if (currentLetter < 'z') currentLetter + 1 else null
                LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = nextKey
                )
            }
            is Resource.Error -> {
                LoadResult.Error(response.exception)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Char, CocktailEntity>): Char? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}