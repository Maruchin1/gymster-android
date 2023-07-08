package com.maruchin.feature.trainingplans.planlist

import com.maruchin.data.plan.model.Plan

internal sealed interface PlanListUiState {

    object Loading : PlanListUiState

    object NoPlans : PlanListUiState

    data class Loaded(val plans: List<Plan>) : PlanListUiState
}
