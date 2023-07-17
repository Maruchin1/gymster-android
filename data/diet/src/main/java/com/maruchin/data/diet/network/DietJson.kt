package com.maruchin.data.diet.network

import androidx.annotation.Keep
import com.maruchin.data.diet.Diet

@Keep
internal data class DietJson(
    val id: String,
    val name: String,
    val groups: List<GroupJson>,
)

internal fun DietJson.toDomain() = Diet(
    id = id,
    name = name,
    groups = groups.map { it.toDomain() }
)