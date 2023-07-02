package com.maruchin.feature.traininglog.editset

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.core.model.ID

internal const val EDIT_SET_ROUTE = "edit_set"
internal const val SET_ID_ARG = "setId"

internal fun NavGraphBuilder.editSetScreen(onBack: () -> Unit) {
    composable("$EDIT_SET_ROUTE/{$SET_ID_ARG}") {
        val viewModel = hiltViewModel<EditSetViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(state.saved) {
            if (state.saved) onBack()
        }
        EditSetScreen(
            state = state,
            onBack = onBack,
            onChangeWeight = viewModel::changeWeight,
            onChangeReps = viewModel::changeReps,
            onSave = viewModel::save
        )
    }
}

internal fun NavController.navigateToEditSet(setId: ID) {
    navigate("$EDIT_SET_ROUTE/${setId.value}")
}