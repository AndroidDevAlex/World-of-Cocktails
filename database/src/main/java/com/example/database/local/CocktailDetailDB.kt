package com.example.database.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cocktail_details",
    foreignKeys = [
        ForeignKey(
            entity = CocktailDB::class,
            parentColumns = ["idDrink"],
            childColumns = ["idDrink"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("idDrink")
    ]
)
data class CocktailDetailDB(
    @PrimaryKey val idDrink: String,
    val instructions: String,
    val glassType: String,
    val ingredients: String
)