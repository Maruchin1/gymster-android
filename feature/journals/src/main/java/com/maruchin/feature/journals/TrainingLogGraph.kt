package com.maruchin.feature.journals

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.journals.activejournal.activeLogScreen
import com.maruchin.feature.journals.createjournal.createJournalScreen
import com.maruchin.feature.journals.createjournal.navigateToCreateJournal
import com.maruchin.feature.journals.editexercise.editExerciseScreen
import com.maruchin.feature.journals.editexercise.navigateToEditExercise
import com.maruchin.feature.journals.journallist.journalListScreen
import com.maruchin.feature.journals.journallist.navigateToJournalList

fun NavGraphBuilder.trainingLogGraph(navController: NavController) {
    navigation(startDestination = ROUTE_ACTIVE_JOURNAL, route = ROUTE_JOURNALS) {
        activeLogScreen(
            onSelectLog = {
                navController.navigateToJournalList()
            },
            onEditExercise = { training, exercise ->
                navController.navigateToEditExercise(training.id, exercise.id)
            }
        )
        journalListScreen(
            onBack = {
                navController.navigateUp()
            },
            onCreateJournal = {
                navController.navigateToCreateJournal()
            }
        )
        createJournalScreen(
            onBack = {
                navController.navigateUp()
            }
        )
        editExerciseScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}

fun NavController.navigateToTrainingLog(options: NavOptions? = null) {
    navigate(ROUTE_JOURNALS, options)
}