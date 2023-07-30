package com.maruchin.data.diet.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DietEntity::class, GroupEntity::class, RecipeEntity::class, IngredientEntity::class],
    version = 1
)
internal abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDao(): DietDao
    abstract fun recipeDao(): RecipeDao
}