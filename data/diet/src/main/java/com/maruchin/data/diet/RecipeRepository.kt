package com.maruchin.data.diet

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getByDietAndName(dietId: String, name: String): Flow<Recipe?>
}