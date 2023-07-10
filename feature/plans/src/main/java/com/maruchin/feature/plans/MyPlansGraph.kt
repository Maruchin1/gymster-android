package com.maruchin.feature.plans

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.plans.plandetails.navigateToPlanDetails
import com.maruchin.feature.plans.plandetails.planDetailsScreen
import com.maruchin.feature.plans.planlist.planListScreen

fun NavGraphBuilder.myPlansGraph(navController: NavController) {
    navigation(startDestination = "plan-list", route = "plans") {
        planListScreen(
            onOpenPlan = { plan ->
                navController.navigateToPlanDetails(plan.id)
            }
        )
        planDetailsScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}

fun NavController.navigateToMyPlans(options: NavOptions? = null) {
    navigate("plans", options)
}