package com.maruchin.feature.plans.plandetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.ui.requireId
import com.maruchin.data.plan.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class PlanDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    planRepository: PlanRepository,
) : ViewModel() {
    private val planId = savedStateHandle.requireId("planId")
    private val plan = planRepository.getById(planId).filterNotNull()

    val uiState: StateFlow<PlanDetailsUiState> = plan
        .map(PlanDetailsUiState::Success)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PlanDetailsUiState.Loading)
}