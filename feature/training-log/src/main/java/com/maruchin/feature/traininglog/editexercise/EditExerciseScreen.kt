package com.maruchin.feature.traininglog.editexercise

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.model.ID
import com.maruchin.core.ui.BackButton
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.content.ContentLoadingView
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.training.model.Exercise
import com.maruchin.data.training.model.ExerciseSet
import com.maruchin.data.training.model.sampleEmptyTrainingLog
import com.maruchin.data.training.model.sampleTrainingLog
import com.maruchin.feature.activelog.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TEXT_FIELD_DEBOUNCE = 2_000L

@Composable
internal fun EditExerciseScreen(
    state: EditExerciseUiState,
    onBack: () -> Unit,
    onChangeWeight: (ID, Float?) -> Unit,
    onChangeReps: (ID, Int?) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(trainingDayName = state.name, onBack = onBack)
        }
    ) { padding ->
        Crossfade(state.loading, modifier = Modifier.padding(padding)) { loading ->
            if (loading) {
                ContentLoadingView()
            } else {
                EditExerciseContent(
                    state = state,
                    onChangeWeight = onChangeWeight,
                    onChangeReps = onChangeReps,
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(trainingDayName: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = trainingDayName)
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        },
    )
}

@Composable
private fun EditExerciseContent(
    state: EditExerciseUiState,
    onChangeWeight: (ID, Float?) -> Unit,
    onChangeReps: (ID, Int?) -> Unit,
) {
    val pagerState = rememberPagerState(state.selectedExerciseIndex)
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        ExercisesTabRow(
            exercises = state.exercises,
            currentPage = pagerState.currentPage,
            onSelectPage = { page ->
                scope.launch {
                    pagerState.animateScrollToPage(page)
                }
            }
        )
        ExercisesPager(
            exercises = state.exercises,
            pagerState = pagerState,
            onChangeWeight = onChangeWeight,
            onChangeReps = onChangeReps,
        )
    }
}

@Composable
private fun ExercisesTabRow(
    exercises: List<Exercise>,
    currentPage: Int,
    onSelectPage: (Int) -> Unit,
) {
    TabRow(selectedTabIndex = currentPage) {
        exercises.forEachIndexed { index, exercise ->
            Tab(
                selected = index == currentPage,
                text = {
                    Text(text = exercise.number)
                },
                onClick = {
                    onSelectPage(index)
                },
            )
        }
    }
}

@Composable
private fun ExercisesPager(
    exercises: List<Exercise>,
    pagerState: PagerState,
    onChangeWeight: (ID, Float?) -> Unit,
    onChangeReps: (ID, Int?) -> Unit,
) {
    HorizontalPager(pageCount = exercises.size, state = pagerState) { page ->
        val exercise = exercises[page]
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ExerciseView(
                name = exercise.name,
                numOfSets = exercise.sets.size,
                repsRange = exercise.repsRange,
            )
            ExerciseSetsView(
                sets = exercise.sets,
                onChangeWeight = onChangeWeight,
                onChangeReps = onChangeReps,
            )
        }
    }
}

@Composable
private fun ExerciseView(name: String, numOfSets: Int, repsRange: IntRange) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "$numOfSets serie",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "$repsRange powtórzeń",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun ExerciseSetsView(
    sets: List<ExerciseSet>,
    onChangeWeight: (ID, Float?) -> Unit,
    onChangeReps: (ID, Int?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        sets.forEach { set ->
            ExerciseSetView(
                weight = set.weight,
                reps = set.reps,
                onChangeWeight = { weight ->
                    onChangeWeight(set.id, weight)
                },
                onChangeReps = { reps ->
                    onChangeReps(set.id, reps)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ExerciseSetView(
    weight: Float?,
    reps: Int?,
    onChangeWeight: (Float?) -> Unit,
    onChangeReps: (Int?) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        EditableValue(
            value = weight?.toString() ?: "",
            placeholder = stringResource(R.string.weight),
            onValueChange = { value ->
                value.takeIf { it.isNotBlank() }?.toFloatOrNull().let(onChangeWeight)
            },
            modifier = Modifier.weight(1f)
        )
        Text(text = "X", style = MaterialTheme.typography.titleLarge)
        EditableValue(
            value = reps?.toString() ?: "",
            placeholder = stringResource(R.string.reps),
            onValueChange = { value ->
                value.takeIf { it.isNotBlank() }?.toIntOrNull().let(onChangeReps)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun EditableValue(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var valueInput by remember(value) { mutableStateOf(value) }
    LaunchedEffect(valueInput) {
        delay(TEXT_FIELD_DEBOUNCE)
        onValueChange(valueInput)
    }
    TextField(
        value = valueInput,
        onValueChange = { valueInput = it },
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        ),
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
        maxLines = 1,
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
    )
}

@LightAndDarkPreview
@Composable
private fun EditExerciseScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: EditExerciseUiState
) {
    GymsterTheme {
        EditExerciseScreen(
            state = state,
            onBack = {},
            onChangeWeight = { _, _ -> },
            onChangeReps = { _, _ -> },
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<EditExerciseUiState> {
    override val values = sequenceOf(
        EditExerciseUiState(),
        EditExerciseUiState(
            trainingDay = sampleTrainingLog.weeks.first().days.first(),
            selectedExerciseId = sampleTrainingLog.weeks.first().days.first().exercises[2].id,
        ),
        EditExerciseUiState(
            trainingDay = sampleEmptyTrainingLog.weeks.first().days.first(),
            selectedExerciseId = sampleEmptyTrainingLog.weeks.first().days.first().exercises[2].id,
        ),
    )
}