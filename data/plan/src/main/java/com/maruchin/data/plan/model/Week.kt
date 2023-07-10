package com.maruchin.data.plan.model

import com.maruchin.core.model.ID

data class Week(
    val id: ID = ID.random,
    val trainings: List<Training>,
)