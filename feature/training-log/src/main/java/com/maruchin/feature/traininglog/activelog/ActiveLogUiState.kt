package com.maruchin.feature.traininglog.activelog

import com.maruchin.data.training.Log

internal sealed interface ActiveLogUiState {

    object Loading : ActiveLogUiState

    data class Success(val log: Log) : ActiveLogUiState
}
