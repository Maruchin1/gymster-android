package com.maruchin.data.plan.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.PlanDay

internal data class DayWithExercises(
    @Embedded
    val day: DayEntity,
    @Relation(
        entity = ExerciseEntity::class,
        parentColumn = "id",
        entityColumn = "dayId"
    )
    val exercises: List<ExerciseEntity>
)

internal fun DayWithExercises.asDomain() = PlanDay(
    id = ID(day.id),
    name = day.name,
    exercises = exercises.asDomain(),
)

internal fun PlanDay.asEntity(planId: String) = DayWithExercises(
    day = DayEntity(
        id = id.value,
        name = name,
        planId = planId,
    ),
    exercises = exercises.asEntity(id.value)
)

internal fun List<DayWithExercises>.asDomain() = map { it.asDomain() }

internal fun List<PlanDay>.asEntity(planId: String) = map { it.asEntity(planId) }
