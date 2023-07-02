package com.maruchin.feature.traininglog

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.traininglog.activelog.ACTIVE_LOG_ROUTE
import com.maruchin.feature.traininglog.activelog.activeLogScreen
import com.maruchin.feature.traininglog.addnewlog.addNewLogScreen
import com.maruchin.feature.traininglog.addnewlog.navigateToNewLog
import com.maruchin.feature.traininglog.editset.editSetScreen
import com.maruchin.feature.traininglog.editset.navigateToEditSet
import com.maruchin.feature.traininglog.selectlog.navigateToSelectLog
import com.maruchin.feature.traininglog.selectlog.selectLogScreen

const val TRAINING_LOG_ROUTE = "training-log"

fun NavGraphBuilder.trainingLogGraph(navController: NavController) {
    navigation(route = TRAINING_LOG_ROUTE, startDestination = ACTIVE_LOG_ROUTE) {
        activeLogScreen(
            onSelectLog = { navController.navigateToSelectLog() },
            onEditExerciseSet = { navController.navigateToEditSet(it.id) }
        )
        selectLogScreen(
            onBack = { navController.navigateUp() },
            onAddNewLog = { navController.navigateToNewLog() }
        )
        addNewLogScreen(
            onBack = { navController.navigateUp() },
        )
        editSetScreen(
            onBack = { navController.navigateUp() }
        )
    }
}

fun NavController.navigateToTrainingLog(options: NavOptions? = null) {
    navigate(route = TRAINING_LOG_ROUTE, options)
}