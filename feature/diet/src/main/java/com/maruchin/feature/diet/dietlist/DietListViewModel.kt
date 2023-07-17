package com.maruchin.feature.diet.dietlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.diet.DietRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class DietListViewModel @Inject constructor(
    private val dietRepository: DietRepository,
) : ViewModel() {
    private val allDiets = dietRepository.getAll()

    val uiState: StateFlow<DietListUiState> = allDiets
        .map(DietListUiState::Success)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DietListUiState.Loading)
}