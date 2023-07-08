package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
internal data class DayEntity(
    @PrimaryKey val id: String,
    val name: String,
    val weekId: String,
)
