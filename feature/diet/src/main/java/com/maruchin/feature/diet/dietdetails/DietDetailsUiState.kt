package com.maruchin.feature.diet.dietdetails

import com.maruchin.data.diet.Diet

internal sealed interface DietDetailsUiState {

    object Loading : DietDetailsUiState

    data class Success(val diet: Diet) : DietDetailsUiState
}