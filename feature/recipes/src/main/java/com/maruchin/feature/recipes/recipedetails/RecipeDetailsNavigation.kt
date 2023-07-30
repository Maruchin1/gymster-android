package com.maruchin.feature.recipes.recipedetails

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.feature.recipes.ARG_RECIPE_ID
import com.maruchin.feature.recipes.ROUTE_RECIPE_DETAILS

internal fun NavGraphBuilder.recipeDetailsScreen(onBack: () -> Unit) {
    composable("$ROUTE_RECIPE_DETAILS/{$ARG_RECIPE_ID}") {
        val viewModel: RecipeDetailsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        RecipeDetailsScreen(
            state = state,
            onBack = onBack,
            onAddToPlanned = viewModel::addToPlanned,
            onRemoveFromPlanned = viewModel::removeFromPlanned,
            onCloseMessage = viewModel::closeMessage
        )
    }
}

internal fun NavController.navigateToRecipeDetails(recipeId: String) {
    navigate("$ROUTE_RECIPE_DETAILS/$recipeId")
}
