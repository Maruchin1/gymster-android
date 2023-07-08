package com.maruchin.feature.trainingplans.createplan

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class CreatePlanViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CreatePlanUiState())
    val uiState = _uiState.asStateFlow()
}