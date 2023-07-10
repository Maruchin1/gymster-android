package com.maruchin.feature.plans.planlist

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.data.plan.model.Plan

fun NavGraphBuilder.planListScreen(onOpenPlan: (Plan) -> Unit) {
    composable("plan-list") {
        val viewModel: PlanListViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        PlanListScreen(
            state = state,
            onOpenPlan = onOpenPlan,
        )
    }
}
