package com.maruchin.data.diet.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maruchin.data.diet.Macro

@Entity
internal data class RecipeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @Embedded
    val macro: Macro,
    val steps: String,
    val tips: String,
    val isPlanned: Boolean,
    val groupId: String,
)
