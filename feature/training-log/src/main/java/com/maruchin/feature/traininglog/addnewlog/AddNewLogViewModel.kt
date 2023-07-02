package com.maruchin.feature.traininglog.addnewlog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.model.ID
import com.maruchin.data.training.LogRepository
import com.maruchin.data.training.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddNewLogViewModel @Inject constructor(
    private val planRepository: PlanRepository,
    private val logRepository: LogRepository,
) : ViewModel() {
    private val myPlans = planRepository.getAll()
    private val logName = MutableStateFlow("")
    private val selectedPlanId = MutableStateFlow<ID?>(null)
    private val logCreated = MutableStateFlow(false)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("AddNewLogViewModel", "Error", throwable)
    }

    val uiState: StateFlow<AddNewLogUiState> = combine(
        myPlans,
        logName,
        selectedPlanId,
        logCreated,
        ::AddNewLogUiState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AddNewLogUiState())

    fun changeLogName(name: String) {
        logName.value = name
    }

    fun selectPlan(planId: ID) {
        selectedPlanId.value = planId
    }

    fun createLog() = viewModelScope.launch(exceptionHandler) {
        val logName = logName.value.also { logName ->
            check(logName.isNotBlank())
        }
        val selectedPlan = selectedPlanId.value?.let { planId ->
            planRepository.getById(planId).first()
        }.let { plan ->
            checkNotNull(plan)
        }
        logRepository.createNew(logName, selectedPlan)
        logCreated.value = true
    }
}