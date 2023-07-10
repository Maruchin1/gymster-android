package com.maruchin.feature.plans.plandetails

import com.maruchin.data.plan.model.Plan

internal sealed interface PlanDetailsUiState {

    object Loading : PlanDetailsUiState

    data class Success(val plan: Plan) : PlanDetailsUiState
}
