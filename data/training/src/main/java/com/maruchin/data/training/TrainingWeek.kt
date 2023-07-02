package com.maruchin.data.training

import com.maruchin.core.model.ID

data class TrainingWeek(
    val id: ID = ID.random(),
    val number: Int,
    val days: List<TrainingDay>,
)
