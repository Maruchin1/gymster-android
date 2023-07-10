package com.maruchin.data.plan.model

import com.maruchin.core.model.ID

data class Training(
    val id: ID = ID.random,
    val name: String,
    val exercises: List<Exercise>,
)