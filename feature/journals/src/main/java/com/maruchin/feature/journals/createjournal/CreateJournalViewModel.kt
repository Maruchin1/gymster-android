package com.maruchin.feature.journals.createjournal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.ui.loggingCoroutineExceptionHandler
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.repository.PlanRepository
import com.maruchin.data.training.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CreateJournalViewModel @Inject constructor(
    private val planRepository: PlanRepository,
    private val journalRepository: JournalRepository,
) : ViewModel() {
    private val exceptionHandler = loggingCoroutineExceptionHandler()
    private val _uiState = MutableStateFlow(CreateJournalUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun changeName(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
    }

    fun selectPlan(plan: Plan) {
        _uiState.update {
            it.copy(selectedPlan = plan)
        }
    }

    fun save() = viewModelScope.launch(exceptionHandler) {
        val logName = _uiState.value.name
        val selectedPlan = _uiState.value.selectedPlan?.let { plan ->
            planRepository.getById(plan.id).first()
        }
        check(logName.isNotBlank())
        checkNotNull(selectedPlan)
        journalRepository.create(logName, selectedPlan)
        _uiState.update {
            it.copy(journalSaved = true)
        }
    }

    private fun loadData() = viewModelScope.launch(exceptionHandler) {
        val plans = planRepository.getAll().first()
        _uiState.update {
            it.copy(plans = plans, selectedPlan = plans.firstOrNull())
        }
    }
}