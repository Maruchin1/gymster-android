package com.maruchin.feature.diet

import com.maruchin.data.diet.Macro

internal fun Macro.format() = "${calories}kcal, ${protein}b, ${fat}t, ${carbs}w"