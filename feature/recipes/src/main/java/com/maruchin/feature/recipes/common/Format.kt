package com.maruchin.feature.recipes.common

import com.maruchin.data.diet.Ingredient
import com.maruchin.data.diet.Macro

internal fun Macro.format() = "${calories}kcal, ${protein}b, ${fat}t, ${carbs}w"

internal fun Ingredient.format() = "$name ${amount}g"