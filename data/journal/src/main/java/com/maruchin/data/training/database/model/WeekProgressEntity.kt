package com.maruchin.data.training.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class WeekProgressEntity(
    @PrimaryKey val id: String,
    val journalId: String,
)
