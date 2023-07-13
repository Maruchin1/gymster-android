package com.maruchin.feature.journals.editexercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.training.repository.SetProgressRepository
import com.maruchin.data.training.repository.TrainingProgressRepository
import com.maruchin.feature.journals.ARG_EXERCISE_PROGRESS_ID
import com.maruchin.feature.journals.ARG_TRAINING_PROGRESS_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    trainingProgressRepository: TrainingProgressRepository,
    private val setProgressRepository: SetProgressRepository,
) : ViewModel() {
    private val trainingId: String = requireNotNull(savedStateHandle[ARG_TRAINING_PROGRESS_ID])
    private val exerciseId: String = requireNotNull(savedStateHandle[ARG_EXERCISE_PROGRESS_ID])
    private val training = trainingProgressRepository.getById(trainingId).filterNotNull()
    private val initialExercise = training.map { training ->
        training.exercisesProgress.find { it.id == exerciseId }
    }.filterNotNull()

    val uiState: StateFlow<EditExerciseUiState> = combine(
        training,
        initialExercise,
        EditExerciseUiState::Success
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), EditExerciseUiState.Loading)

    fun changeWeight(setId: String, weight: Float?) = viewModelScope.launch {
        setProgressRepository.updateWeight(setId, weight)
    }

    fun changeReps(setId: String, reps: Int?) = viewModelScope.launch {
        setProgressRepository.updateReps(setId, reps)
    }
}