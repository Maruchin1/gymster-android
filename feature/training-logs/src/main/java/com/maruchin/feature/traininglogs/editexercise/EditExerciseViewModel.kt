package com.maruchin.feature.traininglogs.editexercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.model.ID
import com.maruchin.data.training.repository.JournalSetRepository
import com.maruchin.data.training.repository.JournalDayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    journalDayRepository: JournalDayRepository,
    private val journalSetRepository: JournalSetRepository,
) : ViewModel() {
    private val trainingDay = journalDayRepository.getById(savedStateHandle.trainingDayId)
    private val exerciseId = flowOf(savedStateHandle.exerciseId)

    val uiState: StateFlow<EditExerciseUiState> = combine(
        trainingDay,
        exerciseId,
        ::EditExerciseUiState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), EditExerciseUiState())

    fun changeWeight(setId: ID, weight: Float?) = viewModelScope.launch {
        journalSetRepository.updateWeight(setId, weight)
    }

    fun changeReps(setId: ID, reps: Int?) = viewModelScope.launch {
        journalSetRepository.updateReps(setId, reps)
    }
}