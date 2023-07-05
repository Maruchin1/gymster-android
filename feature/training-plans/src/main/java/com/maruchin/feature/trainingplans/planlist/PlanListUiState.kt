package com.maruchin.feature.trainingplans.planlist

import com.maruchin.data.training.model.Plan

data class PlanListUiState(
    val plans: List<Plan> = emptyList(),
    val message: String = "",
)
