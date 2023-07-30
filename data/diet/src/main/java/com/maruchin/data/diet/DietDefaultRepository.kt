package com.maruchin.data.diet

import com.maruchin.data.diet.database.DietDao
import com.maruchin.data.diet.database.DietWithGroups
import com.maruchin.data.diet.database.asDomain
import com.maruchin.data.diet.firebase.DietFirebaseDataSource
import com.maruchin.data.diet.firebase.asEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DietDefaultRepository @Inject constructor(
    private val dietDao: DietDao,
    private val dietFirebaseDataSource: DietFirebaseDataSource,
    private val applicationScope: CoroutineScope,
) : DietRepository {

    init {
        loadUserData()
    }

    override fun getAll(): Flow<List<Diet>> {
        return dietDao.selectAll().map {
            it.map(DietWithGroups::asDomain)
        }
    }

    override fun getById(id: String): Flow<Diet?> {
        return dietDao.selectById(id).map { it?.asDomain() }
    }

    private fun loadUserData() = applicationScope.launch {
        val dietsJson = dietFirebaseDataSource.getAll()
        dietDao.insertMissing(dietsJson.map { it.asEntity() })
    }
}