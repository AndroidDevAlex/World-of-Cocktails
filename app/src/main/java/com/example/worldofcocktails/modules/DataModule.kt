package com.example.worldofcocktails.modules

import com.example.database.local.CocktailLocalDataSource
import com.example.worldofcocktails.data.api.ApiManager
import com.example.worldofcocktails.data.repository.CocktailManagerRepository
import com.example.worldofcocktails.data.repository.CocktailManagerRepositoryImpl
import com.example.worldofcocktails.domain.useCases.detailCase.GetCocktailDetailUseCase
import com.example.worldofcocktails.domain.useCases.homeCase.GetCocktailByNameUseCase
import com.example.worldofcocktails.domain.useCases.homeCase.GetCocktailsByApiUseCase
import com.example.worldofcocktails.domain.useCases.homeCase.SaveCocktailUseCase
import com.example.worldofcocktails.domain.useCases.libraryCase.DeleteCocktailUseCase
import com.example.worldofcocktails.domain.useCases.libraryCase.GetCocktailsByDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCocktailManagerRepository(apiManager: ApiManager, localDataSource: CocktailLocalDataSource): CocktailManagerRepository {
        return CocktailManagerRepositoryImpl(apiManager, localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetCocktailsByApiUseCase(repository: CocktailManagerRepository): GetCocktailsByApiUseCase {
        return GetCocktailsByApiUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveCocktailsByApiUseCase(repository: CocktailManagerRepository): SaveCocktailUseCase {
        return SaveCocktailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCocktailByNameUseCase(repository: CocktailManagerRepository): GetCocktailByNameUseCase {
        return GetCocktailByNameUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideGetCocktailsByDBUseCase(repository: CocktailManagerRepository): GetCocktailsByDBUseCase {
        return GetCocktailsByDBUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteCocktailUseCase(repository: CocktailManagerRepository): DeleteCocktailUseCase {
        return DeleteCocktailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCocktailDetailUseCase(repository: CocktailManagerRepository): GetCocktailDetailUseCase {
        return GetCocktailDetailUseCase(repository)
    }
}