package com.maruchin.data.diet

data class Group(
    val name: String,
    val macro: Macro,
    val recipes: List<Recipe>,
)