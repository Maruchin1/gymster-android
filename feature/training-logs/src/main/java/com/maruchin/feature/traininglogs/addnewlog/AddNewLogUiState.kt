package com.maruchin.feature.traininglogs.addnewlog

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingPlan

internal data class AddNewLogUiState(
    val myTrainingPlans: List<TrainingPlan> = emptyList(),
    val logName: String = "",
    val selectedPlanId: ID? = null,
    val logCreated: Boolean = false,
)
