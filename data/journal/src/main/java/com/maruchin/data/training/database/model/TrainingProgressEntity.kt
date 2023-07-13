package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class TrainingProgressEntity(
    @PrimaryKey val id: String,
    val name: String,
    val weekProgressId: String,
)
