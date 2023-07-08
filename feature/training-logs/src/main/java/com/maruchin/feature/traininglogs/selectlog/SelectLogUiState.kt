package com.maruchin.feature.traininglogs.selectlog

import com.maruchin.data.training.model.Journal

internal data class SelectLogUiState(
    val savedJournals: List<Journal> = emptyList(),
    val logSelected: Boolean = false,
    val status: Status = if (savedJournals.isEmpty()) Status.NO_SAVED_LOGS else Status.LOADED,
) {

    enum class Status {
        LOADING,
        NO_SAVED_LOGS,
        LOADED
    }
}
