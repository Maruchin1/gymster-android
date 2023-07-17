package com.maruchin.data.diet.network

import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val DIETS = "diets"
private const val ONE_MEGABYTE = 1024L * 1024L

internal class DietNetworkDataSource @Inject constructor(
    private val storage: FirebaseStorage,
    private val gson: Gson,
) {

    suspend fun getAll(): List<DietJson> {
        return storage.reference
            .child(DIETS)
            .listAll()
            .await()
            .items
            .map { it.getBytes(ONE_MEGABYTE).await() }
            .map { String(it) }
            .map { gson.fromJson(it, DietJson::class.java) }
    }
}