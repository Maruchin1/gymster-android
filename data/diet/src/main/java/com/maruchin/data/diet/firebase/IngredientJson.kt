package com.maruchin.data.diet.firebase

import androidx.annotation.Keep
import com.maruchin.data.diet.database.IngredientEntity
import java.util.UUID

@Keep
internal data class IngredientJson(
    val name: String,
    val amount: Int?
)

internal fun IngredientJson.asEntity(recipeId: String) = IngredientEntity(
    id = UUID.randomUUID().toString(),
    name = name,
    amount = amount,
    recipeId = recipeId,
)
