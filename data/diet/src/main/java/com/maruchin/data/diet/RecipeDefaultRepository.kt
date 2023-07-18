package com.maruchin.data.diet

import com.maruchin.data.diet.network.DietNetworkDataSource
import com.maruchin.data.diet.network.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RecipeDefaultRepository @Inject constructor(
    private val dietNetworkDataSource: DietNetworkDataSource,
) : RecipeRepository {

    override fun getByDietAndName(dietId: String, name: String): Flow<Recipe?> {
        return dietNetworkDataSource.getRecipeByDietAndName(dietId, name).map { it?.toDomain() }
    }
}