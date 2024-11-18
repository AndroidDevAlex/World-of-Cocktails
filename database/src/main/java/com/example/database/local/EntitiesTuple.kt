package com.example.database.local

import androidx.room.Embedded
import androidx.room.Relation

data class EntitiesTuple(
    @Embedded val cocktailDB: CocktailDB,
    @Relation(
        parentColumn = "idDrink",
        entityColumn = "idDrink"
    )
    val cocktailDetailDB: CocktailDetailDB
)