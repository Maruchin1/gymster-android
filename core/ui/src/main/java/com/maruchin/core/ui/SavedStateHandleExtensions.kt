package com.maruchin.core.ui

import androidx.lifecycle.SavedStateHandle
import com.maruchin.core.model.ID

fun SavedStateHandle.requireId(key: String) = get<String>(key).let(::requireNotNull).let(::ID)