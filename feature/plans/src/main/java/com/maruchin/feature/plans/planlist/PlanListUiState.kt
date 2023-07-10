package com.maruchin.feature.plans.planlist

import com.maruchin.data.plan.model.Plan

internal sealed interface PlanListUiState {

    object Loading : PlanListUiState

    data class Success(val plans: List<Plan>) : PlanListUiState
}
