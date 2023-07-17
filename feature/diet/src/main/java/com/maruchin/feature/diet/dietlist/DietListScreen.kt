package com.maruchin.feature.diet.dietlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.Diet
import com.maruchin.data.diet.sampleDiets
import com.maruchin.feature.diet.R

@Composable
internal fun DietListScreen(state: DietListUiState, onOpenDiet: (Diet) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                DietListUiState.Loading -> LoadingContent()

                is DietListUiState.Success -> DietListContent(
                    diets = state.diets,
                    onOpenDiet = onOpenDiet
                )
            }
        }
    }
}

@Composable
private fun TopAppBar() {
    TopAppBar(
        title = {
            Text(text = "Dieta")
        }
    )
}

@Composable
private fun DietListContent(diets: List<Diet>, onOpenDiet: (Diet) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(diets) { diet ->
            DietItem(name = diet.name, onOpen = { onOpenDiet(diet) })
        }
    }
}

@Composable
private fun DietItem(name: String, onOpen: () -> Unit) {
    OutlinedCard(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.diet_cover),
            contentDescription = null
        )
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@LightAndDarkPreview
@Composable
private fun MyDietsScreenPreview(@PreviewParameter(UiStateProvider::class) state: DietListUiState) {
    GymsterTheme {
        DietListScreen(state = state, onOpenDiet = {})
    }
}

private class UiStateProvider : PreviewParameterProvider<DietListUiState> {
    override val values = sequenceOf(
        DietListUiState.Loading,
        DietListUiState.Success(diets = sampleDiets)
    )
}