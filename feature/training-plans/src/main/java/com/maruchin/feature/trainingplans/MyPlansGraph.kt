package com.maruchin.feature.trainingplans

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.trainingplans.createplan.createPlanScreen
import com.maruchin.feature.trainingplans.createplan.navigateToCreatePlan
import com.maruchin.feature.trainingplans.planlist.PLAN_LIST_ROUTE
import com.maruchin.feature.trainingplans.planlist.planListScreen

const val MY_PLANS_ROUTE = "my-plans"

fun NavGraphBuilder.myPlansGraph(navController: NavController) {
    navigation(startDestination = PLAN_LIST_ROUTE, route = MY_PLANS_ROUTE) {
        planListScreen(
            onCreatePlan = {
                navController.navigateToCreatePlan()
            }
        )
        createPlanScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}

fun NavController.navigateToMyPlans(options: NavOptions? = null) {
    navigate(MY_PLANS_ROUTE, options)
}