package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.data.plan.model.Sets
import com.maruchin.data.training.model.ExerciseProgress

internal data class ExerciseWithSets(
    @Embedded
    val exercise: ExerciseProgressEntity,
    @Relation(parentColumn = "id", entityColumn = "exerciseProgressId")
    val setsProgress: List<SetProgressEntity>,
)

internal fun ExerciseWithSets.asDomain() = ExerciseProgress(
    id = exercise.id,
    name = exercise.name,
    sets = Sets(exercise.standardSets, exercise.dropSets),
    repsRange = exercise.minReps..exercise.maxReps,
    rest = exercise.rest,
    setsProgress = setsProgress.asDomain(),
)

internal fun ExerciseProgress.asEntity(trainingDayId: String) = ExerciseWithSets(
    exercise = ExerciseProgressEntity(
        id = id,
        name = name,
        standardSets = sets.standard,
        dropSets = sets.drop,
        minReps = repsRange.first,
        maxReps = repsRange.last,
        rest = rest,
        trainingProgressId = trainingDayId,
    ),
    setsProgress = setsProgress.asEntity(id),
)

internal fun List<ExerciseWithSets>.asDomain() = map { it.asDomain() }

internal fun List<ExerciseProgress>.asEntity(trainingDayId: String) = map { it.asEntity(trainingDayId) }
