package com.maruchin.data.training.model

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Exercise

data class JournalExercise(
    val id: ID = ID.random,
    val number: String,
    val name: String,
    val repsRange: IntRange,
    val sets: List<JournalSet>,
) {

    constructor(exercise: Exercise) : this(
        number = "",
        name = exercise.name,
        repsRange = exercise.repsRange,
        sets = emptyList()
    )
}
