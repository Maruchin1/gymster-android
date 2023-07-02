package com.maruchin.data.training

import android.content.Context
import androidx.room.Room
import com.maruchin.data.training.database.TrainingDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataTrainingModule {

    @Binds
    fun trainingPlanRepository(impl: DefaultPlanRepository): PlanRepository

    @Binds
    fun trainingLogRepository(impl: DefaultLogRepository): LogRepository

    @Binds
    fun exerciseSetRepository(impl: DefaultExerciseSetRepository): ExerciseSetRepository

    companion object {

        @Provides
        @Singleton
        fun trainingDatabase(@ApplicationContext context: Context): TrainingDatabase {
            return Room.databaseBuilder(
                context,
                TrainingDatabase::class.java,
                "training_database"
            ).build()
        }

        @Provides
        fun logDao(database: TrainingDatabase) = database.logDao()

        @Provides
        fun exerciseSetDao(database: TrainingDatabase) = database.exerciseSetDao()
    }
}