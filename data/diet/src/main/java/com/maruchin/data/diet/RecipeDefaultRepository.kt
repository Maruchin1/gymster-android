package com.maruchin.data.diet

import com.maruchin.data.diet.database.RecipeDao
import com.maruchin.data.diet.database.RecipeWithIngredients
import com.maruchin.data.diet.database.asDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RecipeDefaultRepository @Inject constructor(
    private val recipeDao: RecipeDao,
) : RecipeRepository {

    override fun getById(id: String): Flow<Recipe?> {
        return recipeDao.getById(id).map { it?.asDomain() }
    }

    override fun getPlanned(): Flow<List<Recipe>> {
        return recipeDao.getPlanned().map { it.map(RecipeWithIngredients::asDomain) }
    }

    override suspend fun addToPlanned(id: String) {
        recipeDao.addToMenu(id)
    }

    override suspend fun removeFromPlanned(id: String) {
        recipeDao.removeFromMenu(id)
    }
}