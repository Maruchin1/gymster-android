package com.maruchin.data.diet

import com.maruchin.data.diet.network.DietNetworkDataSource
import com.maruchin.data.diet.network.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DietDefaultRepository @Inject constructor(
    private val dietNetworkDataSource: DietNetworkDataSource,
) : DietRepository {

    override fun getAll(): Flow<List<Diet>> {
        return dietNetworkDataSource.getAll().map { diets ->
            diets.map { it.toDomain() }
        }
    }

    override fun getById(id: String): Flow<Diet?> {
        return dietNetworkDataSource.getById(id).map { it?.toDomain() }
    }
}