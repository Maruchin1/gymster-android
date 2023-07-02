package com.maruchin.data.training.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.TrainingDay

@Entity(tableName = "training_days")
internal data class DatabaseTrainingDay(
    @PrimaryKey val id: String,
    val name: String,
    val trainingWeekId: String,
)

internal data class DatabaseTrainingDayWithExercises(
    @Embedded
    val day: DatabaseTrainingDay,
    @Relation(entity = DatabaseExercise::class, parentColumn = "id", entityColumn = "trainingDayId")
    val exercises: List<DatabaseExerciseWithSeries>
)

internal fun DatabaseTrainingDayWithExercises.toExternal() = TrainingDay(
    id = ID(day.id),
    name = day.name,
    exercises = exercises.toExternal(),
)

internal fun List<DatabaseTrainingDayWithExercises>.toExternal() = map { it.toExternal() }

internal fun TrainingDay.toDatabase(trainingWeekId: ID) = DatabaseTrainingDayWithExercises(
    day = DatabaseTrainingDay(
        id = id.value,
        name = name,
        trainingWeekId = trainingWeekId.value,
    ),
    exercises = exercises.toDatabase(id),
)

internal fun List<TrainingDay>.toDatabase(trainingWeekId: ID) =
    map { it.toDatabase(trainingWeekId) }
