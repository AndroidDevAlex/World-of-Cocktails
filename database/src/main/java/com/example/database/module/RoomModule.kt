package com.example.database.module

import android.content.Context
import androidx.room.Room
import com.example.database.local.CocktailDetailDao
import com.example.database.local.CocktailLocalDataSource
import com.example.database.local.CocktailsDao
import com.example.database.local.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideCocktailsDB(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            "local_database"
        ).build()
    }

    @Provides
    @Singleton
    fun getCocktailDao(database: DataBase): CocktailsDao {
        return database.getCocktailsDao()
    }

    @Provides
    @Singleton
    fun getCocktailDetailDao(database: DataBase): CocktailDetailDao {
        return database.getDetailCocktailDao()
    }

    @Provides
    @Singleton
    fun provideCocktailLocalDataSource(
        cocktailsDao: CocktailsDao,
        cocktailDetailDao: CocktailDetailDao
    ): CocktailLocalDataSource {
        return CocktailLocalDataSource(cocktailsDao, cocktailDetailDao)
    }
}