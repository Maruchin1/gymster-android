package com.maruchin.data.diet.network

import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private const val DIETS = "diets"
private const val ONE_MEGABYTE = 1024L * 1024L

@Singleton
internal class DietNetworkDataSource @Inject constructor(
    private val storage: FirebaseStorage,
    private val gson: Gson,
    private val applicationScope: CoroutineScope,
) {

    private val diets: Flow<List<DietJson>> = flow {
        storage.reference
            .child(DIETS)
            .listAll()
            .await()
            .items
            .map { it.getBytes(ONE_MEGABYTE).await() }
            .map { String(it) }
            .map { gson.fromJson(it, DietJson::class.java) }
            .let { emit(it) }
    }.shareIn(applicationScope, SharingStarted.Lazily, replay = 1)

    fun getAll(): Flow<List<DietJson>> {
        return diets
    }

    fun getById(id: String): Flow<DietJson?> {
        return diets.map { diets ->
            diets.find { it.id == id }
        }
    }

    fun getRecipeByDietAndName(dietId: String, name: String): Flow<RecipeJson?> {
        return diets.map { diets ->
            diets.find { it.id == dietId }?.groups?.flatMap { it.recipes }?.find { it.name == name }
        }
    }
}