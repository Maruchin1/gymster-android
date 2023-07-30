package com.maruchin.gymster.feature.bottomnav

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.feature.recipes.ROUTE_RECIPES
import com.maruchin.gymster.feature.menu.ROUTE_MENU

@Composable
fun GymsterNavigationBar(currentDestination: NavDestination?, onOpen: (String) -> Unit) {

    fun isRouteSelected(route: String): Boolean {
        return currentDestination?.hierarchy?.any { it.route == route } == true
    }

    NavigationBar {
        DietItem(
            selected = isRouteSelected(ROUTE_RECIPES),
            onClick = { onOpen(ROUTE_RECIPES) }
        )
        TrainingItem(
            selected = false,
            onClick = {}
        )
        MenuItem(
            selected = isRouteSelected(ROUTE_MENU),
            onClick = { onOpen(ROUTE_MENU) }
        )
        AccountItem(
            selected = false,
            onClick = {}
        )
    }
}

@Composable
private fun RowScope.DietItem(selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null)
        },
        label = {
            Text(text = "Przepisy")
        }
    )
}

@Composable
private fun RowScope.TrainingItem(selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null)
        },
        label = {
            Text(text = "Treningi")
        },
    )
}

@Composable
private fun RowScope.MenuItem(selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(imageVector = Icons.Default.ShoppingBasket, contentDescription = null)
        },
        label = {
            Text(text = "Zakupy")
        }
    )
}

@Composable
private fun RowScope.AccountItem(selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
        },
        label = {
            Text(text = "Konto")
        }
    )
}

@Preview
@Composable
private fun GymsterNavigationBarPreview() {
    GymsterTheme {
        GymsterNavigationBar(currentDestination = null, onOpen = {})
    }
}
