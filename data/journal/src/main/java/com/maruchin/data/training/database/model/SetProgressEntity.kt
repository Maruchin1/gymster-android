package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maruchin.data.training.model.SetProgress

@Entity
internal data class SetProgressEntity(
    @PrimaryKey val id: String,
    val weight: Float?,
    val reps: Int?,
    val exerciseProgressId: String,
)

internal fun SetProgressEntity.asDomain() = SetProgress(
    id = id,
    weight = weight,
    reps = reps
)

internal fun SetProgress.asEntity(exerciseId: String) = SetProgressEntity(
    id = id,
    weight = weight,
    reps = reps,
    exerciseProgressId = exerciseId,
)

internal fun List<SetProgressEntity>.asDomain() = map { it.asDomain() }

internal fun List<SetProgress>.asEntity(exerciseId: String) = map { it.asEntity(exerciseId) }
