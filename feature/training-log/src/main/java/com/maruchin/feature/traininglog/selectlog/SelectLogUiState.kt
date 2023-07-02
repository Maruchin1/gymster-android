package com.maruchin.feature.traininglog.selectlog

import com.maruchin.data.training.Log

internal data class SelectLogUiState(
    val savedLogs: List<Log> = emptyList(),
    val logSelected: Boolean = false,
)
