package com.maruchin.data.diet.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RecipeDao {

    @Query("SELECT * FROM RecipeEntity WHERE id = :id")
    fun getById(id: String): Flow<RecipeWithIngredients?>

    @Query("SELECT * FROM RecipeEntity WHERE isPlanned = 1")
    fun getPlanned(): Flow<List<RecipeWithIngredients>>

    @Query("UPDATE RecipeEntity SET isPlanned = 1 WHERE id = :id")
    suspend fun addToMenu(id: String)

    @Query("UPDATE RecipeEntity SET isPlanned = 0 WHERE id = :id")
    suspend fun removeFromMenu(id: String)

    @Insert
    suspend fun insert(recipes: List<RecipeEntity>)
}