package com.maruchin.gymster

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {

    @Provides
    fun resources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    fun applicationScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}