package com.maruchin.data.diet.firebase

import androidx.annotation.Keep
import com.maruchin.data.diet.Macro

@Keep
internal data class MacroJson(
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int
)

internal fun MacroJson.asEntity() = Macro(
    calories = calories,
    protein = protein,
    fat = fat,
    carbs = carbs,
)
