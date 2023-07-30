package com.maruchin.data.diet.database

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.data.diet.Group

internal data class GroupWithRecipes(
    @Embedded
    val group: GroupEntity,
    @Relation(entity = RecipeEntity::class, entityColumn = "groupId", parentColumn = "id")
    val recipes: List<RecipeWithIngredients>,
)

internal fun GroupWithRecipes.asDomain() = Group(
    id = group.id,
    name = group.name,
    macro = group.macro,
    recipes = recipes.map { it.asDomain() }
)
