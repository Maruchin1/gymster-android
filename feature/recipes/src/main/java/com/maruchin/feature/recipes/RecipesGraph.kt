package com.maruchin.feature.recipes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.feature.recipes.dietdetails.dietDetailsScreen
import com.maruchin.feature.recipes.dietdetails.navigateToDietDetails
import com.maruchin.feature.recipes.home.homeScreen
import com.maruchin.feature.recipes.recipedetails.navigateToRecipeDetails
import com.maruchin.feature.recipes.recipedetails.recipeDetailsScreen

fun NavGraphBuilder.recipesGraph(navController: NavController) {
    navigation(startDestination = ROUTE_HOME, route = ROUTE_RECIPES) {
        homeScreen(
            onOpenDiet = { diet ->
                navController.navigateToDietDetails(diet.id)
            },
            onOpenRecipe = { recipe ->
                navController.navigateToRecipeDetails(recipe.id)
            }
        )
        dietDetailsScreen(
            onBack = {
                navController.navigateUp()
            },
            onOpenRecipe = { recipe ->
                navController.navigateToRecipeDetails(recipe.id)
            }
        )
        recipeDetailsScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}
