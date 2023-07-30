package com.maruchin.gymster.feature.menu

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.feature.menu.mymenu.myMenuScreen

fun NavGraphBuilder.menuGraph(navController: NavController) {
    navigation(startDestination = ROUTE_MY_MENU, route = ROUTE_MENU) {
        myMenuScreen()
    }
}