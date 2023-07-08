package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
internal data class ExerciseEntity(
    @PrimaryKey val id: String,
    val number: String,
    val name: String,
    val minReps: Int,
    val maxReps: Int,
    val dayId: String,
)
