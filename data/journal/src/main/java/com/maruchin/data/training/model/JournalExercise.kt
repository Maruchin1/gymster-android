package com.maruchin.data.training.model

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.PlanExercise

data class JournalExercise(
    val id: ID = ID.random,
    val number: String,
    val name: String,
    val repsRange: IntRange,
    val sets: List<JournalSet>,
) {

    constructor(planExercise: PlanExercise) : this(
        number = planExercise.number,
        name = planExercise.name,
        repsRange = planExercise.repsRange,
        sets = (1..planExercise.sets).map { JournalSet() }
    )
}
