package com.maruchin.feature.trainingplans.planlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.plan.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class PlanListViewModel @Inject constructor(
    planRepository: PlanRepository,
) : ViewModel() {
    private val allPlans = planRepository.getAll()

    val uiState: StateFlow<PlanListUiState> = allPlans
        .map { allPlans ->
            if (allPlans.isEmpty()) {
                PlanListUiState.NoPlans
            } else {
                PlanListUiState.Loaded(allPlans)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PlanListUiState.Loading)
}