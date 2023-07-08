package com.maruchin.data.training.model

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.PlanDay

data class JournalWeek(
    val id: ID = ID.random,
    val number: Int,
    val days: List<JournalDay>,
) {

    constructor(week: Int, planDays: List<PlanDay>) : this(
        number = week,
        days = planDays.map { JournalDay(it) }
    )
}
