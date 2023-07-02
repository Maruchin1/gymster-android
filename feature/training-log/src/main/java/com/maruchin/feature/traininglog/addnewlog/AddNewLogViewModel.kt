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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddNewLogViewModel @Inject constructor(
    private val planRepository: PlanRepository,
    private val logRepository: LogRepository,
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("AddNewLogViewModel", "Error", throwable)
    }
    private val _uiState = MutableStateFlow(AddNewLogUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun changeLogName(value: String) {
        _uiState.update {
            it.copy(logName = value)
        }
    }

    fun selectPlan(planId: ID) {
        _uiState.update {
            it.copy(selectedPlanId = planId)
        }
    }

    fun createLog() = viewModelScope.launch(exceptionHandler) {
        val logName = _uiState.value.logName.also { logName ->
            check(logName.isNotBlank())
        }
        val selectedPlan = _uiState.value.selectedPlanId?.let { planId ->
            planRepository.getById(planId).first()
        }.let { plan ->
            checkNotNull(plan)
        }
        logRepository.createNew(logName, selectedPlan)
        _uiState.update {
            it.copy(logCreated = true)
        }
    }

    private fun loadData() = viewModelScope.launch(exceptionHandler) {
        val myPlans = planRepository.getAll().first()
        _uiState.update {
            it.copy(myPlans = myPlans, selectedPlanId = myPlans.firstOrNull()?.id)
        }
    }
}