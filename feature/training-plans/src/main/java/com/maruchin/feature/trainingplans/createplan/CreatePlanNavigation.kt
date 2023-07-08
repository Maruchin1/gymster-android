package com.maruchin.feature.trainingplans.createplan

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

internal const val CREATE_PLAN_ROUTE = "create_plan"

internal fun NavGraphBuilder.createPlanScreen(onBack: () -> Unit) {
    composable(CREATE_PLAN_ROUTE) {
        val viewModel = hiltViewModel<CreatePlanViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        CreatePlanScreen(
            state = state,
            onBack = onBack,
        )
    }
}

internal fun NavController.navigateToCreatePlan(options: NavOptions? = null) {
    navigate(CREATE_PLAN_ROUTE, options)
}
