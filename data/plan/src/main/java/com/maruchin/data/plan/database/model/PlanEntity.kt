package com.maruchin.data.plan.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
internal data class PlanEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val weeks: Int,
)
