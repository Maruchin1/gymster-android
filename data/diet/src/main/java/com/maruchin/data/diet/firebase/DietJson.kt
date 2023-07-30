package com.maruchin.data.diet.firebase

import androidx.annotation.Keep
import com.maruchin.data.diet.database.DietEntity
import com.maruchin.data.diet.database.DietWithGroups
import java.util.UUID

@Keep
internal data class DietJson(
    val name: String,
    val coverUrl: String,
    val groups: List<GroupJson>,
)

internal fun DietJson.asEntity(): DietWithGroups {
    val dietId = UUID.randomUUID().toString()
    return DietWithGroups(
        diet = DietEntity(
            id = dietId,
            name = name,
            coverUrl = coverUrl,
        ),
        groups = groups.map { it.asEntity(dietId) }
    )
}
