package com.maruchin.gymster

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.maruchin.feature.journals.ROUTE_JOURNALS
import com.maruchin.feature.plans.plansGraph
import com.maruchin.feature.plans.navigateToPlans
import com.maruchin.feature.journals.navigateToTrainingLog
import com.maruchin.feature.journals.trainingLogGraph
import com.maruchin.feature.plans.ROUTE_PLANS

@Composable
internal fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    fun isRouteSelected(route: String): Boolean {
        return currentDestination?.hierarchy?.any { it.route == route } == true
    }

    fun createNavOptions() = navOptions {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null)
                    },
                    label = {
                        Text(text = "Treningi")
                    },
                    selected = isRouteSelected(ROUTE_JOURNALS),
                    onClick = {
                        navController.navigateToTrainingLog(createNavOptions())
                    },
                )
                NavigationBarItem(
                    icon = {
                        Icon(imageVector = Icons.Default.List, contentDescription = null)
                    },
                    label = {
                        Text(text = "Plany")
                    },
                    selected = isRouteSelected(ROUTE_PLANS),
                    onClick = {
                        navController.navigateToPlans(createNavOptions())
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(imageVector = Icons.Default.Fastfood, contentDescription = null)
                    },
                    label = {
                        Text(text = "Diety")
                    },
                    selected = false,
                    onClick = { },
                )
                NavigationBarItem(
                    icon = {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
                    },
                    label = {
                        Text(text = "Konto")
                    },
                    selected = false,
                    onClick = { },
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_JOURNALS,
            modifier = Modifier.padding(padding)
        ) {
            trainingLogGraph(navController)
            plansGraph(navController)
        }
    }
}