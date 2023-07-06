package com.maruchin.data.training

import android.content.Context
import androidx.room.Room
import com.maruchin.data.training.database.TrainingDatabase
import com.maruchin.data.training.repository.DefaultExerciseSetRepository
import com.maruchin.data.training.repository.DefaultTrainingLogRepository
import com.maruchin.data.training.repository.DefaultTrainingPlanRepository
import com.maruchin.data.training.repository.DefaultTrainingDayRepository
import com.maruchin.data.training.repository.ExerciseSetRepository
import com.maruchin.data.training.repository.TrainingLogRepository
import com.maruchin.data.training.repository.TrainingPlanRepository
import com.maruchin.data.training.repository.TrainingDayRepository
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
    fun trainingPlanRepository(impl: DefaultTrainingPlanRepository): TrainingPlanRepository

    @Binds
    fun trainingLogRepository(impl: DefaultTrainingLogRepository): TrainingLogRepository

    @Binds
    fun trainingDayRepository(impl: DefaultTrainingDayRepository): TrainingDayRepository

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
        fun logDao(database: TrainingDatabase) = database.trainingLogDao()

        @Provides
        fun trainingDayDao(database: TrainingDatabase) = database.trainingDayDao()

        @Provides
        fun exerciseSetDao(database: TrainingDatabase) = database.exerciseSetDao()
    }
}