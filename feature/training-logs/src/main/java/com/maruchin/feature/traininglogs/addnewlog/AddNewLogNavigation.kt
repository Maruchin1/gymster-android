package com.maruchin.feature.traininglogs.addnewlog

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

internal const val ADD_NEW_LOG_ROUTE = "add_new_log"

internal fun NavGraphBuilder.addNewLogScreen(onBack: () -> Unit) {
    composable(ADD_NEW_LOG_ROUTE) {
        val viewModel = hiltViewModel<AddNewLogViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(state.logCreated) {
            if (state.logCreated) onBack()
        }
        AddNewLogScreen(
            state = state,
            onBack = onBack,
            onChangeLogName = viewModel::changeLogName,
            onSelectPlan = viewModel::selectPlan,
            onCreateLog = viewModel::createLog,
        )
    }
}

internal fun NavController.navigateToNewLog(options: NavOptions? = null) {
    navigate(ADD_NEW_LOG_ROUTE, options)
}