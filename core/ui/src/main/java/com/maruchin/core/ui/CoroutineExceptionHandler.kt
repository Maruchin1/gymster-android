package com.maruchin.core.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

fun ViewModel.loggingCoroutineExceptionHandler() = CoroutineExceptionHandler { _, throwable ->
    Log.e(this::class.java.simpleName, "Error", throwable)
}
