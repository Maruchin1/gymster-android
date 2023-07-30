package com.maruchin.data.diet

import android.content.Context
import androidx.room.Room
import com.maruchin.data.diet.database.DietDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataDietModule {

    @Binds
    fun dietRepository(impl: DietDefaultRepository): DietRepository

    @Binds
    fun recipeRepository(impl: RecipeDefaultRepository): RecipeRepository

    companion object {

        @Provides
        @Singleton
        fun dietDatabase(@ApplicationContext context: Context): DietDatabase {
            return Room.databaseBuilder(context, DietDatabase::class.java, "diet.db")
                .build()
        }

        @Provides
        fun dietDao(database: DietDatabase) = database.dietDao()

        @Provides
        fun recipeDao(database: DietDatabase) = database.recipeDao()
    }
}