package com.maruchin.feature.trainingplans.createplan

import com.maruchin.data.plan.model.PlanDay

internal data class CreatePlanUiState(
    val planName: String = "Nowy plan",
    val days: List<PlanDay> = emptyList(),
)