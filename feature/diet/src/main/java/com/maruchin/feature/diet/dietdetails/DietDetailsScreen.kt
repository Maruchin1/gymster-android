package com.maruchin.feature.diet.dietdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.Group
import com.maruchin.data.diet.Macro
import com.maruchin.data.diet.Recipe
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.diet.R
import com.maruchin.feature.diet.format

@Composable
internal fun DietDetailsScreen(state: DietDetailsUiState, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(onBack = onBack)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                DietDetailsUiState.Loading -> LoadingContent()

                is DietDetailsUiState.Success -> DietDetailsContent(
                    name = state.diet.name,
                    groups = state.diet.groups,
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Dieta")
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        }
    )
}

@Composable
private fun DietDetailsContent(name: String, groups: List<Group>) {
    LazyColumn {
        item {
            Text(
                text = name,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )
        }
        groups.forEach { group ->
            GroupItem(
                name = group.name,
                macro = group.macro,
                recipes = group.recipes,
            )
        }
    }
}

private fun LazyListScope.GroupItem(name: String, macro: Macro, recipes: List<Recipe>) {
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
            DietItem(
                isEven = index % 2 == 0,
                name = recipe.name,
                macro = recipe.macro.format(),
                ingredients = recipe.ingredients,
            )
        }
    }
}

@Composable
private fun DietItem(isEven: Boolean, name: String, macro: String, ingredients: List<String>) {
    val direction = if (isEven) LayoutDirection.Ltr else LayoutDirection.Rtl
    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        Row(horizontalArrangement = if (isEven) Arrangement.Start else Arrangement.End) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)
                    )
                    Text(
                        text = macro,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = "SKŁADNIKI",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)
                    )
                    ingredients.forEach { ingredient ->
                        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                            Text(text = "- ")
                            Text(
                                text = ingredient,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Image(
                    painter = painterResource(R.drawable.recipe_cover),
                    contentDescription = null,
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun DietDetailsScreenPreview() {
    GymsterTheme {
        DietDetailsScreen(state = DietDetailsUiState.Success(sampleDiets[0]), onBack = {})
    }
}