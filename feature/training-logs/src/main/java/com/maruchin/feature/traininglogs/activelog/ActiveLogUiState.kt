package com.maruchin.feature.traininglogs.activelog

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.Journal
import com.maruchin.data.training.model.JournalWeek

internal data class ActiveLogUiState(
    val logName: String = "",
    val weeks: List<JournalWeek> = emptyList(),
    val currentWeekId: ID = ID.empty,
    val status: Status = Status.LOADING,
) {

    constructor(journal: Journal?) : this(
        logName = journal?.name.orEmpty(),
        weeks = journal?.weeks.orEmpty(),
        currentWeekId = journal?.currentWeekId ?: ID.empty,
        status = if (journal == null) Status.NO_ACTIVE_LOG else Status.LOADED,
    )

    enum class Status {
        LOADING, NO_ACTIVE_LOG, LOADED
    }
}
