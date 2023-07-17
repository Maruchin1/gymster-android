package com.maruchin.feature.diet.dietlist

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.data.diet.Diet
import com.maruchin.feature.diet.ROUTE_DIET_LIST

internal fun NavGraphBuilder.dietListScreen(onOpenDiet: (Diet) -> Unit) {
    composable(ROUTE_DIET_LIST) {
        val viewModel: DietListViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        DietListScreen(state = state, onOpenDiet = onOpenDiet)
    }
}
