package com.maruchin.feature.plans.plandetails

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.feature.plans.ARG_PLAN_ID
import com.maruchin.feature.plans.ROUTE_PLAN_DETAILS

internal fun NavGraphBuilder.planDetailsScreen(onBack: () -> Unit) {
    composable("$ROUTE_PLAN_DETAILS/{$ARG_PLAN_ID}") {
        val viewModel: PlanDetailsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        PlanDetailsScreen(
            state = state,
            onBack = onBack,
        )
    }
}

internal fun NavController.navigateToPlanDetails(planId: String) {
    navigate("$ROUTE_PLAN_DETAILS/$planId")
}