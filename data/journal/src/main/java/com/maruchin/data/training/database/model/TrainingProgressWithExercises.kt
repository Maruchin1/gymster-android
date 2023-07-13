package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.data.training.model.TrainingProgress

internal data class TrainingProgressWithExercises(
    @Embedded
    val training: TrainingProgressEntity,
    @Relation(entity = ExerciseProgressEntity::class, parentColumn = "id", entityColumn = "trainingProgressId")
    val exercisesProgress: List<ExerciseWithSets>
)

internal fun TrainingProgressWithExercises.asDomain() = TrainingProgress(
    id = training.id,
    name = training.name,
    exercisesProgress = exercisesProgress.asDomain(),
)

internal fun TrainingProgress.asEntity(trainingWeekId: String) = TrainingProgressWithExercises(
    training = TrainingProgressEntity(
        id = id,
        name = name,
        weekProgressId = trainingWeekId,
    ),
    exercisesProgress = exercisesProgress.asEntity(id),
)

internal fun List<TrainingProgressWithExercises>.asDomain() = map { it.asDomain() }

internal fun List<TrainingProgress>.asEntity(trainingWeekId: String) =
    map { it.asEntity(trainingWeekId) }
