package com.maruchin.feature.planlist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.planlist.planlist.PLAN_LIST_ROUTE
import com.maruchin.feature.planlist.planlist.planListScreen

const val MY_PLANS_ROUTE = "my-plans"

fun NavGraphBuilder.myPlansGraph(navController: NavController) {
    navigation(startDestination = PLAN_LIST_ROUTE, route = MY_PLANS_ROUTE) {
        planListScreen()
    }
}

fun NavController.navigateToMyPlans(options: NavOptions? = null) {
    navigate(MY_PLANS_ROUTE, options)
}