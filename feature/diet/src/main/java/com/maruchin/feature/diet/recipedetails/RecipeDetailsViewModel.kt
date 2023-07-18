package com.maruchin.feature.diet.recipedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.diet.RecipeRepository
import com.maruchin.feature.diet.ARG_DIET_ID
import com.maruchin.feature.diet.ARG_RECIPE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class RecipeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val dietId: String = requireNotNull(savedStateHandle[ARG_DIET_ID])
    private val recipeName: String = requireNotNull(savedStateHandle[ARG_RECIPE_NAME])

    private val recipe = recipeRepository.getByDietAndName(dietId, recipeName).filterNotNull()

    val uiState: StateFlow<RecipeDetailsUiState> = recipe
        .map(RecipeDetailsUiState::Success)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            RecipeDetailsUiState.Loading
        )
}