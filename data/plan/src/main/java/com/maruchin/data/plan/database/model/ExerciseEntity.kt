package com.maruchin.data.plan.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.PlanExercise

@Entity("exercises")
internal data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val number: String,
    val name: String,
    val sets: Int,
    val minReps: Int,
    val maxReps: Int,
    val dayId: String,
)

internal fun ExerciseEntity.asDomain() = PlanExercise(
    id = ID(id),
    number = number,
    name = name,
    sets = sets,
    repsRange = minReps..maxReps,
)

internal fun List<ExerciseEntity>.asDomain() = map { it.asDomain() }

internal fun PlanExercise.asEntity(dayId: String) = ExerciseEntity(
    id = id.value,
    number = number,
    name = name,
    sets = sets,
    minReps = repsRange.first,
    maxReps = repsRange.last,
    dayId = dayId
)

internal fun List<PlanExercise>.asEntity(dayId: String) = map { it.asEntity(dayId) }