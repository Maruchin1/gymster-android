package com.maruchin.feature.trainingplans.planlist

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val PLAN_LIST_ROUTE = "plan-list"

fun NavGraphBuilder.planListScreen(onCreatePlan: () -> Unit) {
    composable(PLAN_LIST_ROUTE) {
        val viewModel = hiltViewModel<PlanListViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        PlanListScreen(
            state = state,
            onCreatePlan = onCreatePlan
        )
    }
}
