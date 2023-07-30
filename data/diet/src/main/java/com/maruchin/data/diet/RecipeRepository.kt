package com.maruchin.data.diet

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getById(id: String): Flow<Recipe?>

    fun getPlanned(): Flow<List<Recipe>>

    suspend fun addToPlanned(id: String)

    suspend fun removeFromPlanned(id: String)
}