package com.maruchin.data.diet.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class DietEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val coverUrl: String,
)