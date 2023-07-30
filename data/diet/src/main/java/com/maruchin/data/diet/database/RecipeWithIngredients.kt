package com.maruchin.data.diet.database

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.data.diet.Recipe

internal data class RecipeWithIngredients(
    @Embedded
    val recipe: RecipeEntity,
    @Relation(entity = IngredientEntity::class, entityColumn = "recipeId", parentColumn = "id")
    val ingredients: List<IngredientEntity>
)

internal fun RecipeWithIngredients.asDomain() = Recipe(
    id = recipe.id,
    name = recipe.name,
    macro = recipe.macro,
    ingredients = ingredients.map { it.asDomain() },
    steps = recipe.steps.split(";"),
    tips = recipe.tips.split(";"),
    isPlanned = recipe.isPlanned,
)