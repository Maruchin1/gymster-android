package com.maruchin.feature.plans.planlist

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.data.plan.model.Plan
import com.maruchin.feature.plans.ROUTE_PLAN_LIST

fun NavGraphBuilder.planListScreen(onOpenPlan: (Plan) -> Unit) {
    composable(ROUTE_PLAN_LIST) {
        val viewModel: PlanListViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        PlanListScreen(
            state = state,
            onOpenPlan = onOpenPlan,
        )
    }
}
