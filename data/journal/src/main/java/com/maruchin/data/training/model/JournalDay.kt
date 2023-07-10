package com.maruchin.data.training.model

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Training

data class JournalDay(
    val id: ID = ID.random,
    val name: String,
    val exercises: List<JournalExercise> = emptyList(),
) {

    constructor(training: Training) : this(
        name = training.name,
        exercises = training.exercises.map { JournalExercise(it) }
    )
}
