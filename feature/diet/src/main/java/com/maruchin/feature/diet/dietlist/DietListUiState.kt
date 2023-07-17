package com.maruchin.feature.diet.dietlist

import com.maruchin.data.diet.Diet

internal sealed interface DietListUiState {

    object Loading : DietListUiState

    data class Success(val diets: List<Diet>) : DietListUiState
}
