package com.maruchin.feature.traininglogs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.traininglogs.activelog.ACTIVE_LOG_ROUTE
import com.maruchin.feature.traininglogs.activelog.activeLogScreen
import com.maruchin.feature.traininglogs.addnewlog.addNewLogScreen
import com.maruchin.feature.traininglogs.addnewlog.navigateToNewLog
import com.maruchin.feature.traininglogs.editexercise.editExerciseScreen
import com.maruchin.feature.traininglogs.editexercise.navigateToEditExercise
import com.maruchin.feature.traininglogs.selectlog.navigateToSelectLog
import com.maruchin.feature.traininglogs.selectlog.selectLogScreen

const val TRAINING_LOG_ROUTE = "training-log"

fun NavGraphBuilder.trainingLogGraph(navController: NavController) {
    navigation(route = TRAINING_LOG_ROUTE, startDestination = ACTIVE_LOG_ROUTE) {
        activeLogScreen(
            onSelectLog = {
                navController.navigateToSelectLog()
            },
            onEditExercise = { trainingDay, exercise ->
                navController.navigateToEditExercise(trainingDay.id, exercise.id)
            }
        )
        selectLogScreen(
            onBack = {
                navController.navigateUp()
            },
            onAddNewLog = {
                navController.navigateToNewLog()
            }
        )
        addNewLogScreen(
            onBack = {
                navController.navigateUp()
            },
        )
        editExerciseScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}

fun NavController.navigateToTrainingLog(options: NavOptions? = null) {
    navigate(route = TRAINING_LOG_ROUTE, options)
}