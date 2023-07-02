package com.maruchin.feature.traininglog.editset

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.model.ID
import com.maruchin.data.training.ExerciseSetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditSetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exerciseSetRepository: ExerciseSetRepository,
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("EditSetViewModel", "Error", throwable)
    }
    private val setId = savedStateHandle.get<String>(SET_ID_ARG).let(::requireNotNull).let(::ID)
    private val _uiState = MutableStateFlow(EditSetUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadSet()
    }

    fun changeWeight(value: String) {
        _uiState.update {
            it.copy(weightInput = value)
        }
    }

    fun changeReps(value: String) {
        _uiState.update {
            it.copy(repsInput = value)
        }
    }

    fun save() = viewModelScope.launch(exceptionHandler) {
        val weight = _uiState.value.weightInput.toFloat()
        val reps = _uiState.value.repsInput.toInt()
        exerciseSetRepository.update(setId, weight, reps)
        _uiState.update {
            it.copy(saved = true)
        }
    }

    private fun loadSet() = viewModelScope.launch(exceptionHandler) {
        val set = exerciseSetRepository.getById(setId).first().let(::checkNotNull)
        _uiState.update {
            it.copy(
                weightInput = set.weight?.toString() ?: "",
                repsInput = set.reps?.toString() ?: ""
            )
        }
    }
}