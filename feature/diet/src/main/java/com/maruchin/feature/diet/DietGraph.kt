package com.maruchin.feature.diet

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.maruchin.feature.diet.dietdetails.dietDetailsScreen
import com.maruchin.feature.diet.dietdetails.navigateToDietDetails
import com.maruchin.feature.diet.dietlist.dietListScreen

fun NavGraphBuilder.dietGraph(navController: NavController) {
    navigation(startDestination = ROUTE_DIET_LIST, route = ROUTE_DIET) {
        dietListScreen(
            onOpenDiet = { diet ->
                navController.navigateToDietDetails(diet.id)
            }
        )
        dietDetailsScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}

fun NavController.navigateToDiet(options: NavOptions? = null) {
    navigate(ROUTE_DIET, options)
}