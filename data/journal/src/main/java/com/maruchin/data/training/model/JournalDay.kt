package com.maruchin.data.training.model

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.PlanDay

data class JournalDay(
    val id: ID = ID.random,
    val name: String,
    val exercises: List<JournalExercise> = emptyList(),
) {

    constructor(planDay: PlanDay) : this(
        name = planDay.name,
        exercises = planDay.exercises.map { JournalExercise(it) }
    )
}
