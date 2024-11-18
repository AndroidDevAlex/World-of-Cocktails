package com.example.database.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        CocktailDB::class,
        CocktailDetailDB::class
    ]
)
abstract class DataBase: RoomDatabase() {

    abstract fun getCocktailsDao(): CocktailsDao

    abstract fun getDetailCocktailDao(): CocktailDetailDao
}