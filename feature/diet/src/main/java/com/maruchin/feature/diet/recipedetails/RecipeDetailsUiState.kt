package com.maruchin.feature.diet.recipedetails

import com.maruchin.data.diet.Recipe

internal sealed interface RecipeDetailsUiState {

    object Loading : RecipeDetailsUiState

    data class Success(val recipe: Recipe) : RecipeDetailsUiState
}
