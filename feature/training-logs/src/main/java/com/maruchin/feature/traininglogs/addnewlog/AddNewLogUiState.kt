package com.maruchin.feature.traininglogs.addnewlog

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.Plan

internal data class AddNewLogUiState(
    val myPlans: List<Plan> = emptyList(),
    val logName: String = "",
    val selectedPlanId: ID? = null,
    val logCreated: Boolean = false,
)
