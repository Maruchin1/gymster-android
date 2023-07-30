package com.maruchin.feature.recipes.dietdetails

import android.content.res.Resources
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.diet.DietRepository
import com.maruchin.data.diet.Recipe
import com.maruchin.data.diet.RecipeRepository
import com.maruchin.feature.recipes.ARG_DIET_ID
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
internal class DietDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dietRepository: DietRepository,
    private val recipeRepository: RecipeRepository,
    private val resources: Resources,
) : ViewModel() {
    private val dietId: String = requireNotNull(savedStateHandle[ARG_DIET_ID])
    private val diet = dietRepository.getById(dietId).filterNotNull()
    private val message = MutableStateFlow<String?>(null)

    val uiState: StateFlow<DietDetailsUiState> = combine(
        diet,
        message,
        ::DietDetailsUiState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DietDetailsUiState())

    fun addRecipeToPlanned(recipe: Recipe) = viewModelScope.launch {
        recipeRepository.addToPlanned(recipe.id)
        message.value = resources.getString(R.string.message_recipe_added_to_planned)
    }

    fun removeRecipeFromPlanned(recipe: Recipe) = viewModelScope.launch {
        recipeRepository.removeFromPlanned(recipe.id)
        message.value = resources.getString(R.string.message_recipe_removed_from_planned)
    }
}