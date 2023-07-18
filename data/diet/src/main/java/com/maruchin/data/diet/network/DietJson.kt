package com.maruchin.data.diet.network

import androidx.annotation.Keep
import com.maruchin.data.diet.Diet
import java.net.URL

@Keep
internal data class DietJson(
    val id: String,
    val name: String,
    val coverUrl: String,
    val groups: List<GroupJson>,
)

internal fun DietJson.toDomain() = Diet(
    id = id,
    name = name,
    cover = URL(coverUrl),
    groups = groups.map { it.toDomain() }
)