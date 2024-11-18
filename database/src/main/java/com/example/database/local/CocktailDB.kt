package com.example.database.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailDB(
    @PrimaryKey val idDrink: String,
    val name: String,
    val image: String?,
    val category: String,
    val alcoholType: String,
    val isBookmarked: Boolean,
)