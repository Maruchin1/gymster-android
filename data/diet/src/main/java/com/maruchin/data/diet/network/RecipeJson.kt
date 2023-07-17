package com.maruchin.data.diet.network

import androidx.annotation.Keep
import com.maruchin.data.diet.Recipe

@Keep
internal data class RecipeJson(
    val name: String,
    val macro: MacroJson,
    val ingredients: List<String>,
    val steps: List<String>,
    val tips: List<String>,
)

internal fun RecipeJson.toDomain() = Recipe(
    name = name,
    macro = macro.toDomain(),
    ingredients = ingredients,
    steps = steps,
    tips = tips,
)
