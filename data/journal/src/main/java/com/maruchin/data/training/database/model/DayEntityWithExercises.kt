package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.JournalDay

internal data class DayEntityWithExercises(
    @Embedded
    val day: DayEntity,
    @Relation(entity = ExerciseEntity::class, parentColumn = "id", entityColumn = "dayId")
    val exercises: List<ExerciseEntityWithSets>
)

internal fun DayEntityWithExercises.asDomain() = JournalDay(
    id = ID(day.id),
    name = day.name,
    exercises = exercises.asDomain(),
)

internal fun JournalDay.asEntity(trainingWeekId: ID) = DayEntityWithExercises(
    day = DayEntity(
        id = id.value,
        name = name,
        weekId = trainingWeekId.value,
    ),
    exercises = exercises.asEntity(id),
)

internal fun List<DayEntityWithExercises>.asDomain() = map { it.asDomain() }

internal fun List<JournalDay>.asEntity(trainingWeekId: ID) =
    map { it.asEntity(trainingWeekId) }
