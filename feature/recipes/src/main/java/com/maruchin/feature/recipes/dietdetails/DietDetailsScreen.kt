package com.maruchin.feature.recipes.dietdetails

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.Macro
import com.maruchin.data.diet.Recipe
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.recipes.common.RecipeItem
import com.maruchin.feature.recipes.common.format

@Composable
internal fun DietDetailsScreen(
    state: DietDetailsUiState,
    onBack: () -> Unit,
    onOpenRecipe: (Recipe) -> Unit,
    onAddRecipeToPlanned: (Recipe) -> Unit,
    onRemoveRecipeFromPlanned: (Recipe) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(scrollBehavior = scrollBehavior, onBack = onBack)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item {
                AsyncImage(
                    model = state.diet?.cover?.toString(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 4f)
                )
            }
            state.diet?.groups?.forEach { group ->
                GroupItem(
                    name = group.name,
                    macro = group.macro,
                    recipes = group.recipes,
                    onOpenRecipe = onOpenRecipe,
                    onAddRecipeToPlanned = onAddRecipeToPlanned,
                    onRemoveRecipeFromPlanned = onRemoveRecipeFromPlanned,
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = "Przepisy")
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        },
        scrollBehavior = scrollBehavior
    )
}

private fun LazyListScope.GroupItem(
    name: String,
    macro: Macro,
    recipes: List<Recipe>,
    onOpenRecipe: (Recipe) -> Unit,
    onAddRecipeToPlanned: (Recipe) -> Unit,
    onRemoveRecipeFromPlanned: (Recipe) -> Unit,
) {
    item {
        Divider()
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )
        Text(
            text = macro.format(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp)
        )
    }
    recipes.forEachIndexed { index, recipe ->
        item {
            Divider()
            RecipeItem(
                index = index,
                name = recipe.name,
                macro = recipe.macro.format(),
                ingredients = recipe.ingredients.map { it.format() },
                onShowMore = { onOpenRecipe(recipe) },
                isPlanned = recipe.isPlanned,
                onAddToPlanned = { onAddRecipeToPlanned(recipe) },
                onRemoveFromPlanned = { onRemoveRecipeFromPlanned(recipe) }
            )
        }
    }
}

@LightAndDarkPreview
@Composable
private fun DietDetailsScreenPreview() {
    GymsterTheme {
        DietDetailsScreen(
            state = DietDetailsUiState(sampleDiets[0]),
            onBack = {},
            onOpenRecipe = {},
            onAddRecipeToPlanned = {},
            onRemoveRecipeFromPlanned = {}
        )
    }
}