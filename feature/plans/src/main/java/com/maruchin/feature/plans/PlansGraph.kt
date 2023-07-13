package com.maruchin.feature.plans

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.plans.plandetails.navigateToPlanDetails
import com.maruchin.feature.plans.plandetails.planDetailsScreen
import com.maruchin.feature.plans.planlist.planListScreen

fun NavGraphBuilder.plansGraph(navController: NavController) {
    navigation(startDestination = ROUTE_PLAN_LIST, route = ROUTE_PLANS) {
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

fun NavController.navigateToPlans(options: NavOptions? = null) {
    navigate(ROUTE_PLANS, options)
}