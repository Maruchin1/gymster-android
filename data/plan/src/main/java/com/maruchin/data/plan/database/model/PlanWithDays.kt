package com.maruchin.data.plan.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Plan

internal data class PlanWithDays(
    @Embedded
    val plan: PlanEntity,
    @Relation(entity = DayEntity::class, parentColumn = "id", entityColumn = "planId")
    val days: List<DayWithExercises>,
)

internal fun PlanWithDays.asDomain() = Plan(
    id = ID(plan.id),
    name = plan.name,
    weeks = plan.weeks,
    days = days.asDomain()
)

internal fun Plan.asEntity() = PlanWithDays(
    plan = PlanEntity(
        id = id.value,
        name = name,
        weeks = weeks,
    ),
    days = days.asEntity(id.value)
)

internal fun List<PlanWithDays>.asDomain() = map { it.asDomain() }

internal inline fun PlanWithDays.flatten(
    withPlan: (PlanEntity) -> Unit,
    withDays: (List<DayEntity>) -> Unit,
    withExercises: (List<ExerciseEntity>) -> Unit,
) {
    plan.let(withPlan)
    days.let { daysWithExercises ->
        daysWithExercises.map { it.day }.let(withDays)
        daysWithExercises.flatMap { it.exercises }.let(withExercises)
    }
}