package com.maruchin.data.training.model

import com.maruchin.data.plan.model.Week
import java.util.UUID

data class WeekProgress(
    val id: String = UUID.randomUUID().toString(),
    val trainingsProgress: List<TrainingProgress>,
) {

    constructor(week: Week) : this(
        trainingsProgress = week.trainings.map { TrainingProgress(it) }
    )
}
