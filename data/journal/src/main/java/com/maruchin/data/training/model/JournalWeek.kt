package com.maruchin.data.training.model

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Training

data class JournalWeek(
    val id: ID = ID.random,
    val number: Int,
    val days: List<JournalDay>,
) {

    constructor(week: Int, trainings: List<Training>) : this(
        number = week,
        days = trainings.map { JournalDay(it) }
    )
}
