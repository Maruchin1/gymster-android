package com.maruchin.data.training.model

import com.maruchin.data.plan.model.Exercise
import com.maruchin.data.plan.model.Sets
import java.util.UUID

data class ExerciseProgress(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sets: Sets,
    val repsRange: IntRange,
    val rest: String,
    val setsProgress: List<SetProgress>
) {

    constructor(exercise: Exercise) : this(
        name = exercise.name,
        sets = exercise.sets,
        repsRange = exercise.repsRange,
        rest = exercise.rest,
        setsProgress = SetProgress.createMultiple(exercise.sets.total)
    )
}
