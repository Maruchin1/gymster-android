package com.maruchin.data.training

import android.content.Context
import androidx.room.Room
import com.maruchin.data.training.database.JournalDatabase
import com.maruchin.data.training.repository.DefaultSetProgressRepository
import com.maruchin.data.training.repository.DefaultJournalRepository
import com.maruchin.data.training.repository.DefaultTrainingProgressRepository
import com.maruchin.data.training.repository.SetProgressRepository
import com.maruchin.data.training.repository.JournalRepository
import com.maruchin.data.training.repository.TrainingProgressRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataJournalModule {

    @Binds
    fun journalRepository(impl: DefaultJournalRepository): JournalRepository

    @Binds
    fun trainingProgressRepository(impl: DefaultTrainingProgressRepository): TrainingProgressRepository

    @Binds
    fun setProgressRepository(impl: DefaultSetProgressRepository): SetProgressRepository

    companion object {

        @Provides
        @Singleton
        fun trainingDatabase(@ApplicationContext context: Context): JournalDatabase {
            return Room.databaseBuilder(
                context,
                JournalDatabase::class.java,
                "training_database"
            ).build()
        }

        @Provides
        fun journalDao(database: JournalDatabase) = database.journalDao()

        @Provides
        fun weekDao(database: JournalDatabase) = database.weekProgressDao()

        @Provides
        fun dayDao(database: JournalDatabase) = database.trainingProgressDao()

        @Provides
        fun exerciseDao(database: JournalDatabase) = database.exerciseProgressDao()

        @Provides
        fun setDao(database: JournalDatabase) = database.setProgressDao()
    }
}