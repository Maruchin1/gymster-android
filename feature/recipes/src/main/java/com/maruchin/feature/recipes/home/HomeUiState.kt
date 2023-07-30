package com.maruchin.feature.recipes.home

import com.maruchin.data.diet.Diet
import com.maruchin.data.diet.Recipe

internal data class HomeUiState(
    val diets: List<Diet> = emptyList(),
    val plannedRecipes: List<Recipe> = emptyList(),
)
