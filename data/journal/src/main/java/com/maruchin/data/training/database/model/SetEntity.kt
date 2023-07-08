package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.JournalSet

@Entity(tableName = "sets")
internal data class SetEntity(
    @PrimaryKey val id: String,
    val weight: Float?,
    val reps: Int?,
    val exerciseId: String,
)

internal fun SetEntity.asDomain() = JournalSet(
    id = ID(id),
    weight = weight,
    reps = reps
)

internal fun JournalSet.asEntity(exerciseId: ID) = SetEntity(
    id = id.value,
    weight = weight,
    reps = reps,
    exerciseId = exerciseId.value,
)

internal fun List<SetEntity>.asDomain() = map { it.asDomain() }

internal fun List<JournalSet>.asEntity(exerciseId: ID) = map { it.asEntity(exerciseId) }
