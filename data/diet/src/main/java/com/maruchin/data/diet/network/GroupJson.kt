package com.maruchin.data.diet.network

import androidx.annotation.Keep
import com.maruchin.data.diet.Group

@Keep
internal data class GroupJson(
    val name: String,
    val macro: MacroJson,
    val recipes: List<RecipeJson>,
)

internal fun GroupJson.toDomain() = Group(
    name = name,
    macro = macro.toDomain(),
    recipes = recipes.map { it.toDomain() }
)
