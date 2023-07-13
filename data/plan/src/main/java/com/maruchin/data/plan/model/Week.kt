package com.maruchin.data.plan.model

import java.util.UUID

data class Week(
    val id: String = UUID.randomUUID().toString(),
    val trainings: List<Training>,
)