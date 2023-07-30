package com.maruchin.data.diet.database

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.data.diet.Diet
import java.net.URL

internal data class DietWithGroups(
    @Embedded
    val diet: DietEntity,
    @Relation(entity = GroupEntity::class, entityColumn = "dietId", parentColumn = "id")
    val groups: List<GroupWithRecipes>
)

internal fun DietWithGroups.asDomain() = Diet(
    id = diet.id,
    name = diet.name,
    cover = URL(diet.coverUrl),
    groups = groups.map { it.asDomain() },
)
