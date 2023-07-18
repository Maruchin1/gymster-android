package com.maruchin.feature.diet.recipedetails

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.feature.diet.ARG_DIET_ID
import com.maruchin.feature.diet.ARG_RECIPE_NAME
import com.maruchin.feature.diet.ROUTE_RECIPE_DETAILS

internal fun NavGraphBuilder.recipeDetailsScreen(onBack: () -> Unit) {
    composable("$ROUTE_RECIPE_DETAILS/{$ARG_DIET_ID}/{$ARG_RECIPE_NAME}") {
        val viewModel: RecipeDetailsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        RecipeDetailsScreen(state = state, onBack = onBack)
    }
}

internal fun NavController.navigateToRecipeDetails(dietId: String, name: String) {
    navigate("$ROUTE_RECIPE_DETAILS/$dietId/$name")
}
