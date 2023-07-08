package com.maruchin.data.plan

import android.content.Context
import androidx.room.Room
import com.maruchin.data.plan.database.PlanDatabase
import com.maruchin.data.plan.repository.DefaultPlanDayRepository
import com.maruchin.data.plan.repository.DefaultPlanRepository
import com.maruchin.data.plan.repository.PlanDayRepository
import com.maruchin.data.plan.repository.PlanExerciseRepository
import com.maruchin.data.plan.repository.PlanRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataPlanModule {

    @Binds
    fun planRepository(impl: DefaultPlanRepository): PlanRepository

    @Binds
    fun planDayRepository(impl: DefaultPlanDayRepository): PlanDayRepository

    @Binds
    fun planExerciseRepository(impl: PlanExerciseRepository): PlanExerciseRepository

    companion object {

        @Provides
        @Singleton
        fun planDatabase(@ApplicationContext context: Context): PlanDatabase {
            return Room.databaseBuilder(context, PlanDatabase::class.java, "plan_database").build()
        }

        @Provides
        fun planDao(database: PlanDatabase) = database.planDao()

        @Provides
        fun dayDao(database: PlanDatabase) = database.dayDao()

        @Provides
        fun exerciseDao(database: PlanDatabase) = database.exerciseDao()
    }
}