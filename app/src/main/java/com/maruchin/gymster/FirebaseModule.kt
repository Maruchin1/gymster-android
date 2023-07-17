package com.maruchin.gymster

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class FirebaseModule {

    @Provides
    fun remoteConfig() = Firebase.remoteConfig

    @Provides
    fun storage() = Firebase.storage

    @Provides
    @Singleton
    fun gson() = Gson()
}