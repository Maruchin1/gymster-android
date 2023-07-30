package com.maruchin.feature.recipes.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.data.diet.Diet
import com.maruchin.data.diet.Recipe
import com.maruchin.feature.recipes.ROUTE_HOME

internal fun NavGraphBuilder.homeScreen(
    onOpenDiet: (Diet) -> Unit,
    onOpenRecipe: (Recipe) -> Unit
) {
    composable(ROUTE_HOME) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        HomeScreen(
            state = state,
            onOpenDiet = onOpenDiet,
            onOpenRecipe = onOpenRecipe,
            onRemoveFromPlanned = viewModel::removeRecipeFromPlanned,
        )
    }
}
