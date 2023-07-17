package com.maruchin.data.diet

import com.maruchin.data.diet.network.DietJson
import com.maruchin.data.diet.network.DietNetworkDataSource
import com.maruchin.data.diet.network.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DietDefaultRepository @Inject constructor(
    private val dietNetworkDataSource: DietNetworkDataSource,
    private val applicationScope: CoroutineScope,
) : DietRepository {

    private val diets = flow { emit(dietNetworkDataSource.getAll()) }
        .map { it.map(DietJson::toDomain) }
        .shareIn(applicationScope, SharingStarted.Lazily, replay = 1)

    override fun getAll(): Flow<List<Diet>> {
        return diets
    }

    override fun getById(id: String): Flow<Diet?> {
        return diets.map { diets ->
            diets.find { it.id == id }
        }
    }
}