package com.maruchin.feature.journals.createjournal

import com.maruchin.data.plan.model.Plan

internal data class CreateJournalUiState(
    val name: String = "",
    val plans: List<Plan> = emptyList(),
    val selectedPlan: Plan? = null,
    val journalSaved: Boolean = false,
)
