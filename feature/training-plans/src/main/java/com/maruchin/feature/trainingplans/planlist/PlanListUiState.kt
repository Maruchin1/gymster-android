package com.maruchin.feature.trainingplans.planlist

import com.maruchin.data.training.model.TrainingPlan

data class PlanListUiState(
    val trainingPlans: List<TrainingPlan> = emptyList(),
    val message: String = "",
)
