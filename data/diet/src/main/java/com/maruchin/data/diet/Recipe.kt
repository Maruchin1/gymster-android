package com.maruchin.data.diet

data class Recipe(
    val id: String,
    val name: String,
    val macro: Macro,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val tips: List<String>,
    val isPlanned: Boolean,
)