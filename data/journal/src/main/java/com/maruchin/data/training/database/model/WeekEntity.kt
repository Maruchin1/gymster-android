package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weeks")
internal data class WeekEntity(
    @PrimaryKey val id: String,
    val number: Int,
    val journalId: String,
)
