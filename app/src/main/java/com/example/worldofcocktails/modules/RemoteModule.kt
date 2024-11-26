package com.example.worldofcocktails.modules

import com.example.worldofcocktails.data.api.ApiManager
import com.example.worldofcocktails.data.api.CocktailApiService
import com.example.worldofcocktails.util.ConstantUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstantUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCocktailApiService(retrofit: Retrofit): CocktailApiService {
        return retrofit.create(CocktailApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiManager(
        cocktailApiService: CocktailApiService
    ): ApiManager {
        return ApiManager(cocktailApiService)
    }
}