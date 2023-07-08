package com.maruchin.data.training

import android.content.Context
import androidx.room.Room
import com.maruchin.data.training.database.JournalDatabase
import com.maruchin.data.training.repository.DefaultJournalSetRepository
import com.maruchin.data.training.repository.DefaultJournalRepository
import com.maruchin.data.training.repository.DefaultJournalDayRepository
import com.maruchin.data.training.repository.JournalSetRepository
import com.maruchin.data.training.repository.JournalRepository
import com.maruchin.data.training.repository.JournalDayRepository
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
    fun journalDayRepository(impl: DefaultJournalDayRepository): JournalDayRepository

    @Binds
    fun journalSetRepository(impl: DefaultJournalSetRepository): JournalSetRepository

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
        fun weekDao(database: JournalDatabase) = database.weekDao()

        @Provides
        fun dayDao(database: JournalDatabase) = database.dayDao()

        @Provides
        fun exerciseDao(database: JournalDatabase) = database.exerciseDao()

        @Provides
        fun setDao(database: JournalDatabase) = database.setDao()
    }
}