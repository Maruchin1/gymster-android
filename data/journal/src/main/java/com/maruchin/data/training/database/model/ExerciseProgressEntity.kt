package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class ExerciseProgressEntity(
    @PrimaryKey val id: String,
    val name: String,
    val standardSets: Int,
    val dropSets: Int,
    val minReps: Int,
    val maxReps: Int,
    val rest: String,
    val trainingProgressId: String,
)
