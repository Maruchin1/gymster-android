package com.maruchin.feature.recipes.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.diet.DietRepository
import com.maruchin.data.diet.Recipe
import com.maruchin.data.diet.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    dietRepository: DietRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val allDiets = dietRepository.getAll()
    private val plannedRecipes = recipeRepository.getPlanned()

    val uiState: StateFlow<HomeUiState> = combine(
        allDiets,
        plannedRecipes,
        ::HomeUiState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())

    fun removeRecipeFromPlanned(recipe: Recipe) = viewModelScope.launch {
        recipeRepository.removeFromPlanned(recipe.id)
    }
}