package com.maruchin.data.diet.firebase

import androidx.annotation.Keep
import com.maruchin.data.diet.database.RecipeEntity
import com.maruchin.data.diet.database.RecipeWithIngredients
import java.util.UUID

@Keep
internal data class RecipeJson(
    val name: String,
    val macro: MacroJson,
    val ingredients: List<IngredientJson>,
    val steps: List<String>,
    val tips: List<String>,
)

internal fun RecipeJson.asEntity(groupId: String): RecipeWithIngredients {
    val recipeId = UUID.randomUUID().toString()
    return RecipeWithIngredients(
        recipe = RecipeEntity(
            id = recipeId,
            name = name,
            macro = macro.asEntity(),
            steps = steps.joinToString(separator = ";"),
            tips = tips.joinToString(separator = ";"),
            isPlanned = false,
            groupId = groupId,
        ),
        ingredients = ingredients.map { it.asEntity(recipeId) }
    )
}
