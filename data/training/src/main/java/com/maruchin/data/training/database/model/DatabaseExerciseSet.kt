package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.ExerciseSet

@Entity(tableName = "sets")
internal data class DatabaseExerciseSet(
    @PrimaryKey val id: String,
    val weight: Float?,
    val reps: Int?,
    val exerciseId: String,
)

internal fun DatabaseExerciseSet.toExternal() = ExerciseSet(
    id = ID(id),
    weight = weight,
    reps = reps
)

internal fun List<DatabaseExerciseSet>.toExternal() = map { it.toExternal() }

internal fun ExerciseSet.toDatabase(exerciseId: ID) = DatabaseExerciseSet(
    id = id.value,
    weight = weight,
    reps = reps,
    exerciseId = exerciseId.value,
)

internal fun List<ExerciseSet>.toDatabase(exerciseId: ID) = map { it.toDatabase(exerciseId) }
