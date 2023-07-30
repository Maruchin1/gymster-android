package com.maruchin.data.diet.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DietDao {

    @Query("SELECT * FROM DietEntity")
    fun selectAll(): Flow<List<DietWithGroups>>

    @Query("SELECT * FROM DietEntity WHERE id = :id")
    fun selectById(id: String): Flow<DietWithGroups?>

    @Query("SELECT EXISTS(SELECT * FROM DietEntity WHERE name = :name)")
    suspend fun exists(name: String): Boolean

    @Transaction
    suspend fun insertMissing(dietsWithGroups: List<DietWithGroups>) {
        dietsWithGroups.filter { exists(it.diet.name).not() }.also { dietsWithGroups ->
            insertDiets(dietsWithGroups.map { it.diet })
        }.flatMap { it.groups }.also { groupsWithRecipes ->
            insertGroups(groupsWithRecipes.map { it.group })
        }.flatMap { it.recipes }.also { recipesWithIngredients ->
            insertRecipes(recipesWithIngredients.map { it.recipe })
        }.flatMap { it.ingredients }.also { ingredients ->
            insertIngredients(ingredients)
        }
    }

    @Insert
    suspend fun insertDiets(diets: List<DietEntity>)

    @Insert
    suspend fun insertGroups(groups: List<GroupEntity>)

    @Insert
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Insert

    suspend fun insertIngredients(ingredients: List<IngredientEntity>)
}