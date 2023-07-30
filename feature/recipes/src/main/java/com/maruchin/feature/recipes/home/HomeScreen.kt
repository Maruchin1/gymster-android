package com.maruchin.feature.recipes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.Diet
import com.maruchin.data.diet.Recipe
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.recipes.common.RecipeItem
import com.maruchin.feature.recipes.common.format
import kotlinx.coroutines.launch
import java.net.URL

private const val TAB_ALL = 0
private const val TAB_PLANNED = 1
private const val NUM_OF_TABS = 2

@Composable
internal fun HomeScreen(
    state: HomeUiState,
    onOpenDiet: (Diet) -> Unit,
    onOpenRecipe: (Recipe) -> Unit,
    onRemoveFromPlanned: (Recipe) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DietHomeTabRow(
                selectedTab = pagerState.currentPage,
                onSelectTab = {
                    scope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                }
            )
            HorizontalPager(pageCount = NUM_OF_TABS, state = pagerState) { page ->
                when (page) {
                    TAB_ALL -> AllDietsView(
                        diets = state.diets,
                        nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                        onOpenDiet = onOpenDiet,
                    )

                    TAB_PLANNED -> PlannedRecipesView(
                        recipes = state.plannedRecipes,
                        nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                        onOpenRecipe = onOpenRecipe,
                        onRemoveFromPlanned = onRemoveFromPlanned,
                    )
                }
            }
        }
    }
}

@Composable
private fun TopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Przepisy")
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun DietHomeTabRow(selectedTab: Int, onSelectTab: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTab) {
        Tab(
            selected = selectedTab == TAB_ALL,
            text = {
                Text(text = "Wszystkie")
            },
            onClick = {
                onSelectTab(TAB_ALL)
            },
        )
        Tab(
            selected = selectedTab == TAB_PLANNED,
            text = {
                Text(text = "Zaplanowane")
            },
            onClick = {
                onSelectTab(TAB_PLANNED)
            }
        )
    }
}

@Composable
private fun AllDietsView(
    diets: List<Diet>,
    nestedScrollConnection: NestedScrollConnection,
    onOpenDiet: (Diet) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .fillMaxSize()
    ) {
        items(diets, key = { it.id }) { diet ->
            DietItem(
                cover = diet.cover,
                name = diet.name,
                onOpen = { onOpenDiet(diet) }
            )
        }
    }
}

@Composable
private fun PlannedRecipesView(
    recipes: List<Recipe>,
    nestedScrollConnection: NestedScrollConnection,
    onOpenRecipe: (Recipe) -> Unit,
    onRemoveFromPlanned: (Recipe) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .fillMaxSize()
    ) {
        itemsIndexed(recipes, key = { _, recipe -> recipe.id }) { index, recipe ->
            RecipeItem(
                index = index,
                name = recipe.name,
                macro = recipe.macro.format(),
                ingredients = recipe.ingredients.map { it.format() },
                isPlanned = recipe.isPlanned,
                onAddToPlanned = { },
                onRemoveFromPlanned = {
                    onRemoveFromPlanned(recipe)
                },
                onShowMore = {
                    onOpenRecipe(recipe)
                },
            )
            Divider()
        }
    }
}

@Composable
private fun DietItem(cover: URL, name: String, onOpen: () -> Unit) {
    OutlinedCard(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        AsyncImage(
            model = cover.toString(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
        )
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}

@Preview
@Composable
private fun MyDietsScreenPreview() {
    GymsterTheme {
        HomeScreen(
            state = HomeUiState(diets = sampleDiets),
            onOpenDiet = {},
            onOpenRecipe = {},
            onRemoveFromPlanned = {},
        )
    }
}
