package com.maruchin.feature.traininglog.selectlog

import com.maruchin.data.training.model.TrainingLog

internal data class SelectLogUiState(
    val savedTrainingLogs: List<TrainingLog> = emptyList(),
    val logSelected: Boolean = false,
    val status: Status = if (savedTrainingLogs.isEmpty()) Status.NO_SAVED_LOGS else Status.LOADED,
) {

    enum class Status {
        LOADING,
        NO_SAVED_LOGS,
        LOADED
    }
}
