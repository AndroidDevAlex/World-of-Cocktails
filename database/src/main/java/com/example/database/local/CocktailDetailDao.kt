package com.example.database.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CocktailDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktailDetail(detail: CocktailDetailDB)

    @Transaction
    @Query("SELECT * " +
            "FROM cocktails " +
            "WHERE cocktails.idDrink = :idDrink")
    suspend fun getFullInformation(idDrink: String): EntitiesTuple
}