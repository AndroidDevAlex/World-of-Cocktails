package com.example.worldofcocktails.data.api

import com.example.worldofcocktails.data.models.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {

    /**
     * Response details [here](https://www.thecocktaildb.com/api/json/v1/1/search.php?f=a)
     */
    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") letter: String): CocktailResponse

    /**
     * Response details [here](https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita)
     */
    @GET("search.php")
    suspend fun searchCocktails(@Query("s") query: String): CocktailResponse

    /**
     * Response details [here](https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=11007)
     */
    @GET("lookup.php")
    suspend fun getFullCocktailDetail(@Query("i") id: String): CocktailResponse

}