package com.maruchin.data.diet.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maruchin.data.diet.Macro

@Entity
internal data class GroupEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @Embedded
    val macro: Macro,
    val dietId: String,
)
