package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.JournalExercise

internal data class ExerciseEntityWithSets(
    @Embedded
    val exercise: ExerciseEntity,
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    val sets: List<SetEntity>,
)

internal fun ExerciseEntityWithSets.asDomain() = JournalExercise(
    id = ID(exercise.id),
    number = exercise.number,
    name = exercise.name,
    repsRange = exercise.minReps..exercise.maxReps,
    sets = sets.asDomain(),
)

internal fun JournalExercise.asEntity(trainingDayId: ID) = ExerciseEntityWithSets(
    exercise = ExerciseEntity(
        id = id.value,
        number = number,
        name = name,
        minReps = repsRange.first,
        maxReps = repsRange.last,
        dayId = trainingDayId.value,
    ),
    sets = sets.asEntity(id),
)

internal fun List<ExerciseEntityWithSets>.asDomain() = map { it.asDomain() }

internal fun List<JournalExercise>.asEntity(trainingDayId: ID) = map { it.asEntity(trainingDayId) }
