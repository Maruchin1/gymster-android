package com.maruchin.data.diet.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maruchin.data.diet.Ingredient

@Entity
internal data class IngredientEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val amount: Int?,
    val recipeId: String,
)

internal fun IngredientEntity.asDomain() = Ingredient(
    name = name,
    amount = amount
)