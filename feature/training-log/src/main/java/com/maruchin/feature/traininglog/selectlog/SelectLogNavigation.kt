package com.maruchin.feature.traininglog.selectlog

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

internal const val SELECT_LOG_ROUTE = "select-log"

internal fun NavGraphBuilder.selectLogScreen(onBack: () -> Unit, onAddNewLog: () -> Unit) {
    composable(SELECT_LOG_ROUTE) {
        val viewModel = hiltViewModel<SelectLogViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(state.logSelected) {
            if (state.logSelected) onBack()
        }
        SelectLogScreen(
            state = state,
            onBack = onBack,
            onSelectLog = viewModel::selectLog,
            onAddNewLog = onAddNewLog,
            onDeleteLog = viewModel::deleteLog,
        )
    }
}

internal fun NavController.navigateToSelectLog(options: NavOptions? = null) {
    navigate(SELECT_LOG_ROUTE, options)
}