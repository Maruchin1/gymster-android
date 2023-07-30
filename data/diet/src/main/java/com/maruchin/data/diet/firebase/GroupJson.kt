package com.maruchin.data.diet.firebase

import androidx.annotation.Keep
import com.maruchin.data.diet.database.GroupEntity
import com.maruchin.data.diet.database.GroupWithRecipes
import java.util.UUID

@Keep
internal data class GroupJson(
    val name: String,
    val macro: MacroJson,
    val recipes: List<RecipeJson>,
)

internal fun GroupJson.asEntity(dietId: String): GroupWithRecipes {
    val groupId = UUID.randomUUID().toString()
    return GroupWithRecipes(
        group = GroupEntity(
            id = groupId,
            name = name,
            macro = macro.asEntity(),
            dietId = dietId
        ),
        recipes = recipes.map { it.asEntity(groupId) }
    )
}