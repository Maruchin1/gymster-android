package com.maruchin.feature.trainingplans.createplan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.BackButton
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.plan.model.PlanDay
import com.maruchin.data.plan.model.PlanExercise

@Composable
internal fun CreatePlanScreen(
    state: CreatePlanUiState,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.planName)
                },
                navigationIcon = {
                    BackButton(onClick = onBack)
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            state.days.forEach { trainingDay ->
                item {
                    Text(
                        text = trainingDay.name,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .combinedClickable(
                                onClick = {  },
                                onLongClick = {  }
                            )
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                            .fillMaxWidth()
                    )
                }
                items(trainingDay.exercises) { exercise ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = exercise.name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                        Text(
                            text = "${exercise.sets} serie",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        Text(
                            text = "${exercise.repsRange} powtórzeń",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                item {
                    OutlinedCard(
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        ),
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Dodaj ćwiczenie...",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
            item {
                Text(
                    text = "Dodaj trening...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier
                        .clickable {  }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun CreatePlanScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: CreatePlanUiState
) {
    GymsterTheme {
        CreatePlanScreen(
            state = state,
            onBack = {},
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<CreatePlanUiState> {
    override val values = sequenceOf(
        CreatePlanUiState(),
        CreatePlanUiState(
            planName = "Push Pull",
            days = listOf(
                PlanDay(name = "Push Siła", exercises = emptyList())
            ),
        ),
        CreatePlanUiState(
            planName = "Push Pull",
            days = listOf(
                PlanDay(
                    name = "Push Siła",
                    exercises = listOf(
                        PlanExercise(
                            number = "1",
                            name = "Wyciskanie sztangi na ławce poziomej",
                            repsRange = 8..10,
                            sets = 3,
                        )
                    )
                )
            ),
        )
    )
}