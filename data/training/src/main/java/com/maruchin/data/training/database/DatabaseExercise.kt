package com.maruchin.data.training.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.Exercise

@Entity(tableName = "exercises")
internal data class DatabaseExercise(
    @PrimaryKey val id: String,
    val number: String,
    val name: String,
    val minReps: Int,
    val maxReps: Int,
    val trainingDayId: String,
)

internal data class DatabaseExerciseWithSeries(
    @Embedded
    val exercise: DatabaseExercise,
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    val series: List<DatabaseExerciseSet>,
)

internal fun DatabaseExerciseWithSeries.toExternal() = Exercise(
    id = ID(exercise.id),
    number = exercise.number,
    name = exercise.name,
    repsRange = exercise.minReps..exercise.maxReps,
    exerciseSets = series.toExternal(),
)

internal fun List<DatabaseExerciseWithSeries>.toExternal() = map { it.toExternal() }

internal fun Exercise.toDatabase(trainingDayId: ID) = DatabaseExerciseWithSeries(
    exercise = DatabaseExercise(
        id = id.value,
        number = number,
        name = name,
        minReps = repsRange.first,
        maxReps = repsRange.last,
        trainingDayId = trainingDayId.value,
    ),
    series = exerciseSets.toDatabase(id),
)

internal fun List<Exercise>.toDatabase(trainingDayId: ID) = map { it.toDatabase(trainingDayId) }
