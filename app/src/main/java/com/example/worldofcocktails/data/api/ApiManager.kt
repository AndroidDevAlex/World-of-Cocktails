package com.example.worldofcocktails.data.api

import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Cocktail
import com.example.worldofcocktails.data.mapToDetailEntity
import com.example.worldofcocktails.data.mapToListEntity
import okio.IOException
import retrofit2.HttpException

class ApiManager(
    private val cocktailApiService: CocktailApiService
) {

    suspend fun getListCocktails(letter: String): Resource<List<CocktailEntity>> {
        return try {
            val response = cocktailApiService.getCocktailsByFirstLetter(letter)
            Resource.Success(response.mapToListEntity())
        } catch (e: IOException) {
            Resource.Error(e)
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun searchCocktailByName(query: String): Cocktail {
        return try {
            val response = cocktailApiService.searchCocktails(query)
            val cocktail = response.drinks.firstOrNull()
            if (cocktail != null) {
                Cocktail.Success(cocktail.mapToDetailEntity())
            } else {
                Cocktail.Empty
            }
        } catch (e: IOException) {
            Cocktail.Error(e)
        } catch (e: HttpException) {
            Cocktail.Error(e)
        } catch (e: Exception) {
            Cocktail.Error(e)
        }
    }

    suspend fun getCocktailDetail(id: String): Resource<CocktailEntity> {
        return try {
            val response = cocktailApiService.getFullCocktailDetail(id)
            val cocktail = response.drinks.firstOrNull()
            if (cocktail != null) {
                Resource.Success(cocktail.mapToDetailEntity())
            } else {
                Resource.Error(Throwable("Cocktail not found"))
            }
        } catch (e: IOException) {
            Resource.Error(e)
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}