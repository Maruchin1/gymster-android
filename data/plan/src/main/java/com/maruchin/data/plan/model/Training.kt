package com.maruchin.data.plan.model

import java.util.UUID

data class Training(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val exercises: List<Exercise>,
)