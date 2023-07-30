package com.maruchin.feature.recipes.recipedetails

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.recipes.common.RecipeItem
import com.maruchin.feature.recipes.common.format
import com.maruchin.gymster.feature.recipes.R

@Composable
internal fun RecipeDetailsScreen(
    state: RecipeDetailsUiState,
    onBack: () -> Unit,
    onAddToPlanned: () -> Unit,
    onRemoveFromPlanned: () -> Unit,
    onCloseMessage: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onCloseMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(scrollBehavior = scrollBehavior, onBack = onBack)
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            state.recipe?.let { recipe ->
                RecipeItem(
                    name = recipe.name,
                    macro = recipe.macro.format(),
                    ingredients = recipe.ingredients.map { it.format() },
                    isPlanned = recipe.isPlanned,
                    onAddToPlanned = onAddToPlanned,
                    onRemoveFromPlanned = onRemoveFromPlanned,
                )
                Divider()
                StepList(steps = recipe.steps)
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Divider()
                TipList(tips = recipe.tips)
                Spacer(modifier = Modifier.padding(top = 16.dp))
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

@Preview
@Composable
private fun RecipeDetailsScreenPreview(@PreviewParameter(UiStateProvider::class) state: RecipeDetailsUiState) {
    GymsterTheme {
        RecipeDetailsScreen(
            state = state,
            onBack = {},
            onAddToPlanned = {},
            onRemoveFromPlanned = {},
            onCloseMessage = {},
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<RecipeDetailsUiState> {
    override val values = sequenceOf(
        RecipeDetailsUiState(recipe = sampleDiets[0].groups[0].recipes[0]),
        RecipeDetailsUiState(recipe = sampleDiets[0].groups[0].recipes[0].copy(isPlanned = true))
    )
}
