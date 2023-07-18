package com.maruchin.feature.diet.dietdetails

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.Diet
import com.maruchin.data.diet.Group
import com.maruchin.data.diet.Macro
import com.maruchin.data.diet.Recipe
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.diet.R
import com.maruchin.feature.diet.common.RecipeItem
import com.maruchin.feature.diet.format
import java.net.URL

@Composable
internal fun DietDetailsScreen(
    state: DietDetailsUiState,
    onBack: () -> Unit,
    onOpenRecipe: (Diet, Recipe) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(scrollBehavior = scrollBehavior, onBack = onBack)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                DietDetailsUiState.Loading -> LoadingContent()

                is DietDetailsUiState.Success -> DietDetailsContent(
                    cover = state.diet.cover,
                    groups = state.diet.groups,
                    nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                    onOpenRecipe = { recipe ->
                        onOpenRecipe(state.diet, recipe)
                    },
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(scrollBehavior: TopAppBarScrollBehavior, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.diet))
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun DietDetailsContent(
    cover: URL,
    groups: List<Group>,
    nestedScrollConnection: NestedScrollConnection,
    onOpenRecipe: (Recipe) -> Unit,
) {
    LazyColumn(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
        item {
            AsyncImage(
                model = cover.toString(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
            )
        }
        groups.forEach { group ->
            GroupItem(
                name = group.name,
                macro = group.macro,
                recipes = group.recipes,
                onOpenRecipe = onOpenRecipe,
            )
        }
    }
}

private fun LazyListScope.GroupItem(
    name: String,
    macro: Macro,
    recipes: List<Recipe>,
    onOpenRecipe: (Recipe) -> Unit
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
                ingredients = recipe.ingredients,
                onClick = { onOpenRecipe(recipe) },
            )
        }
    }
}

@LightAndDarkPreview
@Composable
private fun DietDetailsScreenPreview() {
    GymsterTheme {
        DietDetailsScreen(
            state = DietDetailsUiState.Success(sampleDiets[0]),
            onBack = {},
            onOpenRecipe = { _, _ -> }
        )
    }
}