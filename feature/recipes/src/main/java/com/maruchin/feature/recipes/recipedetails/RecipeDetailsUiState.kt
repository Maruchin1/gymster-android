package com.maruchin.feature.recipes.recipedetails

import com.maruchin.data.diet.Recipe

internal data class RecipeDetailsUiState(
    val recipe: Recipe? = null,
    val message: String? = null,
)
