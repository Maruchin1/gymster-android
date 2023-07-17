package com.maruchin.data.diet

data class Recipe(
    val name: String,
    val macro: Macro,
    val ingredients: List<String>,
    val steps: List<String>,
    val tips: List<String>,
)