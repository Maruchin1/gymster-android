package com.maruchin.feature.plans.plandetails

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.core.model.ID

internal fun NavGraphBuilder.planDetailsScreen(onBack: () -> Unit) {
    composable(route = "plan-details/{planId}") {
        val viewModel: PlanDetailsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        PlanDetailsScreen(
            state = state,
            onBack = onBack,
        )
    }
}

internal fun NavController.navigateToPlanDetails(planId: ID) {
    navigate("plan-details/${planId.value}")
}