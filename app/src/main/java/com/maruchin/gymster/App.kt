package com.maruchin.gymster

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.maruchin.feature.recipes.ROUTE_RECIPES
import com.maruchin.feature.recipes.recipesGraph
import com.maruchin.gymster.feature.bottomnav.GymsterNavigationBar
import com.maruchin.gymster.feature.menu.menuGraph

@Composable
internal fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    fun createNavOptions() = navOptions {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    Scaffold(
        bottomBar = {
            GymsterNavigationBar(
                currentDestination = navBackStackEntry?.destination,
                onOpen = { route ->
                    navController.navigate(route, createNavOptions())
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_RECIPES,
            modifier = Modifier.padding(padding)
        ) {
            recipesGraph(navController)
            menuGraph(navController)
        }
    }
}