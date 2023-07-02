package com.maruchin.feature.planlist.planlist

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val PLAN_LIST_ROUTE = "plan-list"

fun NavGraphBuilder.planListScreen() {
    composable(PLAN_LIST_ROUTE) {
        val viewModel = hiltViewModel<PlanListViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        PlanListScreen(
            state = state,
            onCreateNewLog = viewModel::createNewLog,
            onCloseMessage = viewModel::closeMessage,
        )
    }
}

fun NavController.navigateToPlanList(options: NavOptions? = null) {
    navigate(PLAN_LIST_ROUTE, options)
}