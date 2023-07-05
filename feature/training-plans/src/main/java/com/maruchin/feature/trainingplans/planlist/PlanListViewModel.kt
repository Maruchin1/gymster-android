package com.maruchin.feature.trainingplans.planlist

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.model.ID
import com.maruchin.data.training.repository.TrainingLogRepository
import com.maruchin.data.training.repository.TrainingPlanRepository
import com.maruchin.feature.trainingplans.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PlanListViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository,
    private val trainingLogRepository: TrainingLogRepository,
    private val resources: Resources,
) : ViewModel() {
    private val message = MutableStateFlow("")
    private val allPlans = trainingPlanRepository.getAll()

    val uiState: StateFlow<PlanListUiState> = combine(
        allPlans,
        message,
    ) { allPlans, message ->
        PlanListUiState(plans = allPlans, message = message)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        PlanListUiState()
    )

    fun createNewLog(planId: ID, logName: String) = viewModelScope.launch {
        trainingPlanRepository.getById(planId).first()?.let { plan ->
            trainingLogRepository.createNew(logName, plan)
            message.value = resources.getString(R.string.new_log_created)
        }
    }

    fun closeMessage() {
        message.value = ""
    }
}