package com.maruchin.feature.diet.dietdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.diet.DietRepository
import com.maruchin.feature.diet.ARG_DIET_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class DietDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dietRepository: DietRepository,
) : ViewModel() {
    private val dietId: String = requireNotNull(savedStateHandle[ARG_DIET_ID])

    private val diet = dietRepository.getById(dietId).filterNotNull()

    val uiState: StateFlow<DietDetailsUiState> = diet
        .map(DietDetailsUiState::Success)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DietDetailsUiState.Loading)
}