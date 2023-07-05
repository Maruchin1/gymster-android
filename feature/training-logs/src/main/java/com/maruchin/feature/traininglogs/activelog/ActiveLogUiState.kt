package com.maruchin.feature.traininglogs.activelog

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingLog
import com.maruchin.data.training.model.TrainingWeek

internal data class ActiveLogUiState(
    val logName: String = "",
    val weeks: List<TrainingWeek> = emptyList(),
    val currentWeekId: ID = ID.empty,
    val status: Status = Status.LOADING,
) {

    constructor(trainingLog: TrainingLog?) : this(
        logName = trainingLog?.name.orEmpty(),
        weeks = trainingLog?.weeks.orEmpty(),
        currentWeekId = trainingLog?.currentWeekId ?: ID.empty,
        status = if (trainingLog == null) Status.NO_ACTIVE_LOG else Status.LOADED,
    )

    enum class Status {
        LOADING, NO_ACTIVE_LOG, LOADED
    }
}
