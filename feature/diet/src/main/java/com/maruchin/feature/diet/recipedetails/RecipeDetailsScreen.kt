package com.maruchin.feature.diet.recipedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.Recipe
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.diet.R
import com.maruchin.feature.diet.common.RecipeItem
import com.maruchin.feature.diet.format

@Composable
internal fun RecipeDetailsScreen(state: RecipeDetailsUiState, onBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(onBack, scrollBehavior)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                RecipeDetailsUiState.Loading -> LoadingContent()

                is RecipeDetailsUiState.Success -> RecipeDetailsContent(
                    recipe = state.recipe,
                    nestedScrollConnection = scrollBehavior.nestedScrollConnection
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
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
private fun RecipeDetailsContent(recipe: Recipe, nestedScrollConnection: NestedScrollConnection) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .nestedScroll(nestedScrollConnection)
    ) {
        RecipeItem(
            name = recipe.name,
            macro = recipe.macro.format(),
            ingredients = recipe.ingredients,
        )
        Divider()
        StepList(steps = recipe.steps)
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Divider()
        TipList(tips = recipe.tips)
        Spacer(modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
private fun StepList(steps: List<String>) {
    Text(
        text = stringResource(R.string.steps).uppercase(),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
    )
    OrderedList(items = steps)
}

@Composable
private fun TipList(tips: List<String>) {
    Text(
        text = stringResource(R.string.tips).uppercase(),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
    )
    OrderedList(items = tips)
}

@Composable
private fun OrderedList(items: List<String>) {
    items.forEachIndexed { index, item ->
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                text = "${index + 1}. ",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(text = item, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@LightAndDarkPreview
@Composable
private fun RecipeDetailsScreenPreview() {
    GymsterTheme {
        RecipeDetailsScreen(
            state = RecipeDetailsUiState.Success(recipe = sampleDiets[0].groups[0].recipes[0]),
            onBack = {}
        )
    }
}