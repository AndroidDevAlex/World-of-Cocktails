package com.example.worldofcocktails.util

import com.example.database.local.CocktailDB
import com.example.database.local.CocktailDetailDB
import com.example.database.local.EntitiesTuple
import com.example.worldofcocktails.data.models.CocktailResponse
import com.example.worldofcocktails.data.models.Drink
import com.example.worldofcocktails.entityUi.DrinkRecipe
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.entityUi.DetailCocktail

fun Drink.mapToDetailEntity(): CocktailEntity {
    return CocktailEntity(
        idDrink = idDrink,
        name = name,
        image = image,
        category = category,
        alcoholType = alcoholType,
        isBookmarked = false,
        detail = DetailCocktail(
            instructions = instructions,
            glassType = glassType,
            ingredients = toIngredientAndMeasure(this)
        )
    )
}

fun toIngredientAndMeasure(drink: Drink): List<DrinkRecipe> {
    val drinkRecipe = mutableListOf<DrinkRecipe>()

    val ingredientsList = listOf(
        drink.ingredient1, drink.ingredient2, drink.ingredient3, drink.ingredient4, drink.ingredient5,
        drink.ingredient6, drink.ingredient7, drink.ingredient8, drink.ingredient9, drink.ingredient10,
        drink.ingredient11, drink.ingredient12, drink.ingredient13, drink.ingredient14, drink.ingredient15
    )

    val measuresList = listOf(
        drink.measure1, drink.measure2, drink.measure3, drink.measure4, drink.measure5,
        drink.measure6, drink.measure7, drink.measure8, drink.measure9, drink.measure10,
        drink.measure11, drink.measure12, drink.measure13, drink.measure14, drink.measure15
    )

    for (i in ingredientsList.indices) {
        val ingredient = ingredientsList[i]
        val measure = measuresList[i]

        if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
            drinkRecipe.add(DrinkRecipe(ingredient, measure))
        }
    }

    return drinkRecipe
}

fun CocktailResponse.mapToListEntity(): List<CocktailEntity> {
    return drinks.map { drink ->
        CocktailEntity(
            idDrink = drink.idDrink,
            name = drink.name,
            image = drink.image,
            category = drink.category,
            alcoholType = drink.alcoholType,
            isBookmarked = false,
            detail = DetailCocktail(
                instructions = drink.instructions,
                glassType = drink.glassType,
                ingredients = toIngredientAndMeasure(drink)
            )
        )
    }
}

fun DetailCocktail.mapToDB(idDrink: String): CocktailDetailDB {
    return CocktailDetailDB(
        idDrink = idDrink,
        instructions = instructions,
        glassType = glassType,
        ingredients = ingredients.joinToString(", ") {
            "${it.nameIngredient}:${it.measure}"
        }
    )
}

fun CocktailEntity.mapToDB(): CocktailDB {
    return CocktailDB(
        idDrink = idDrink,
        name = name,
        image = image,
        category = category,
        alcoholType = alcoholType,
        isBookmarked = true,
    )
}


fun CocktailDetailDB.mapToDetailUi(): DetailCocktail {
    return DetailCocktail(
        instructions = instructions,
        glassType = glassType,
        ingredients = ingredients.split(", ").map {
            val parts = it.split(":")
            DrinkRecipe(parts[0], parts.getOrElse(1) { "" })
        }
    )
}

 fun EntitiesTuple.toCocktailEntity(): CocktailEntity {
    return CocktailEntity(
        idDrink = cocktailDB.idDrink,
        name = cocktailDB.name,
        image = cocktailDB.image,
        category = cocktailDB.category,
        alcoholType = cocktailDB.alcoholType,
        isBookmarked = cocktailDB.isBookmarked,
        detail = cocktailDetailDB.mapToDetailUi()
    )
}

fun CocktailDB.mapFromDBToUiShort(): CocktailEntity {
    return CocktailEntity(
        idDrink = idDrink,
        name = name,
        image = image,
        category = category,
        alcoholType = alcoholType,
        isBookmarked = isBookmarked,
        detail = null
    )
}