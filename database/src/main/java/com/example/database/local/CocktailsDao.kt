package com.example.database.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(cocktail: CocktailDB)

    @Query("SELECT * FROM cocktails")
     fun getOllCocktail(): Flow<List<CocktailDB>>

    @Query("DELETE FROM cocktails WHERE idDrink = :id")
    suspend fun deleteDrinkById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM cocktails WHERE idDrink = :id)")
    suspend fun isCocktailSaved(id: String): Boolean
}