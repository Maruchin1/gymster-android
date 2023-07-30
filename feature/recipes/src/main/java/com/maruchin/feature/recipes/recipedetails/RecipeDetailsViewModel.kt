package com.maruchin.feature.recipes.recipedetails

import android.content.res.Resources
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.diet.RecipeRepository
import com.maruchin.feature.recipes.ARG_RECIPE_ID
import com.maruchin.gymster.feature.recipes.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RecipeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val resources: Resources,
) : ViewModel() {
    private val recipeId: String = requireNotNull(savedStateHandle[ARG_RECIPE_ID])
    private val recipe = recipeRepository.getById(recipeId).filterNotNull()
    private val message = MutableStateFlow<String?>(null)

    val uiState: StateFlow<RecipeDetailsUiState> = combine(
        recipe,
        message,
        ::RecipeDetailsUiState
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        RecipeDetailsUiState()
    )

    fun addToPlanned() = viewModelScope.launch {
        recipeRepository.addToPlanned(recipeId)
        message.value = resources.getString(R.string.message_recipe_added_to_planned)
    }

    fun removeFromPlanned() = viewModelScope.launch {
        recipeRepository.removeFromPlanned(recipeId)
        message.value = resources.getString(R.string.message_recipe_removed_from_planned)
    }

    fun closeMessage() {
        message.value = null
    }
}