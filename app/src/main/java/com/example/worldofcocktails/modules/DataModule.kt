package com.example.worldofcocktails.modules

import com.example.database.local.CocktailDetailDao
import com.example.database.local.CocktailsDao
import com.example.worldofcocktails.data.api.ApiManager
import com.example.worldofcocktails.data.repository.DetailRepository
import com.example.worldofcocktails.data.repository.DetailRepositoryImpl
import com.example.worldofcocktails.data.repository.HomeRepository
import com.example.worldofcocktails.data.repository.HomeRepositoryImpl
import com.example.worldofcocktails.data.repository.LibraryRepository
import com.example.worldofcocktails.data.repository.LibraryRepositoryImpl
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
    fun provideHomeRepository(apiManager: ApiManager, cocktailsDao: CocktailsDao, cocktailDetailDao: CocktailDetailDao ): HomeRepository {
        return HomeRepositoryImpl(apiManager, cocktailsDao, cocktailDetailDao)
    }

    @Provides
    @Singleton
    fun provideLibraryRepository(cocktailsDao: CocktailsDao): LibraryRepository {
        return LibraryRepositoryImpl(cocktailsDao)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(apiManager: ApiManager, cocktailDetailDao: CocktailDetailDao): DetailRepository {
        return DetailRepositoryImpl(apiManager, cocktailDetailDao)
    }

    @Provides
    @Singleton
    fun provideGetCocktailsByApiUseCase(homeRepository: HomeRepository): GetCocktailsByApiUseCase {
        return GetCocktailsByApiUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideSaveCocktailsByApiUseCase(homeRepository: HomeRepository): SaveCocktailUseCase {
        return SaveCocktailUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideGetCocktailByNameUseCase(homeRepository: HomeRepository): GetCocktailByNameUseCase {
        return GetCocktailByNameUseCase(homeRepository)
    }


    @Provides
    @Singleton
    fun provideGetCocktailsByDBUseCase(libraryRepository: LibraryRepository): GetCocktailsByDBUseCase {
        return GetCocktailsByDBUseCase(libraryRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteCocktailUseCase(libraryRepository: LibraryRepository): DeleteCocktailUseCase {
        return DeleteCocktailUseCase(libraryRepository)
    }

    @Provides
    @Singleton
    fun provideGetCocktailDetailUseCase(detailRepository: DetailRepository): GetCocktailDetailUseCase {
        return GetCocktailDetailUseCase(detailRepository)
    }
}