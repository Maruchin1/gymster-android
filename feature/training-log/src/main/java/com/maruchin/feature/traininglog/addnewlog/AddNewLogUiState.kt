package com.maruchin.feature.traininglog.addnewlog

import com.maruchin.core.model.ID
import com.maruchin.data.training.Plan

internal data class AddNewLogUiState(
    val myPlans: List<Plan> = emptyList(),
    val logName: String = "",
    val selectedPlanId: ID? = null,
    val logCreated: Boolean = false,
)
