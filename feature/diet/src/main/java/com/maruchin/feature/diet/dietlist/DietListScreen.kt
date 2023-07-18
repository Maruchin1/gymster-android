package com.maruchin.feature.diet.dietlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.Diet
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.diet.R
import java.net.URL

@Composable
internal fun DietListScreen(state: DietListUiState, onOpenDiet: (Diet) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                DietListUiState.Loading -> LoadingContent()

                is DietListUiState.Success -> DietListContent(
                    diets = state.diets,
                    nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                    onOpenDiet = onOpenDiet
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.diet))
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun DietListContent(
    diets: List<Diet>,
    nestedScrollConnection: NestedScrollConnection,
    onOpenDiet: (Diet) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(diets) { diet ->
            DietItem(
                cover = diet.cover,
                name = diet.name,
                onOpen = { onOpenDiet(diet) }
            )
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

@LightAndDarkPreview
@Composable
private fun MyDietsScreenPreview() {
    GymsterTheme {
        DietListScreen(state = DietListUiState.Success(diets = sampleDiets), onOpenDiet = {})
    }
}
