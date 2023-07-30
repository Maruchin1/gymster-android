package com.maruchin.feature.recipes.dietdetails

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.data.diet.Recipe
import com.maruchin.feature.recipes.ARG_DIET_ID
import com.maruchin.feature.recipes.ROUTE_DIET_DETAILS

internal fun NavGraphBuilder.dietDetailsScreen(
    onBack: () -> Unit,
    onOpenRecipe: (Recipe) -> Unit
) {
    composable("$ROUTE_DIET_DETAILS/{$ARG_DIET_ID}") {
        val viewModel: DietDetailsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        DietDetailsScreen(
            state = state,
            onBack = onBack,
            onOpenRecipe = onOpenRecipe,
            onAddRecipeToPlanned = viewModel::addRecipeToPlanned,
            onRemoveRecipeFromPlanned = viewModel::removeRecipeFromPlanned,
        )
    }
}

internal fun NavController.navigateToDietDetails(dietId: String) {
    navigate("$ROUTE_DIET_DETAILS/$dietId")
}