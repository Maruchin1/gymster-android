package com.maruchin.feature.planlist.planlist

import com.maruchin.data.training.Plan

data class PlanListUiState(
    val plans: List<Plan> = emptyList(),
    val message: String = "",
)
