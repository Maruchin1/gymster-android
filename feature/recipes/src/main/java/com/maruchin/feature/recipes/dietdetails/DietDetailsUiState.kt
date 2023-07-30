package com.maruchin.feature.recipes.dietdetails

import com.maruchin.data.diet.Diet

internal data class DietDetailsUiState(
    val diet: Diet? = null,
    val message: String? = null,
)