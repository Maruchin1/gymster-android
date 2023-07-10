package com.maruchin.data.plan

import com.maruchin.data.plan.repository.DefaultPlanRepository
import com.maruchin.data.plan.repository.PlanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataPlanModule {

    @Binds
    fun planRepository(impl: DefaultPlanRepository): PlanRepository
}