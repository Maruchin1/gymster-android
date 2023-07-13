package com.maruchin.feature.journals.editexercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.training.model.ExerciseProgress
import com.maruchin.data.training.model.SetProgress
import com.maruchin.data.training.model.sampleCompleteJournal
import com.maruchin.data.training.model.sampleEmptyJournal
import com.maruchin.feature.journals.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TEXT_FIELD_DEBOUNCE = 2_000L

@Composable
internal fun EditExerciseScreen(
    state: EditExerciseUiState,
    onBack: () -> Unit,
    onChangeWeight: (String, Float?) -> Unit,
    onChangeReps: (String, Int?) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                trainingDayName = when (state) {
                    is EditExerciseUiState.Success -> state.training.name
                    else -> ""
                },
                onBack = onBack
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                EditExerciseUiState.Loading -> LoadingContent()

                is EditExerciseUiState.Success -> EditExerciseContent(
                    exercises = state.training.exercisesProgress,
                    initialExerciseIndex = state.getInitialExerciseIndex(),
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
    exercises: List<ExerciseProgress>,
    initialExerciseIndex: Int,
    onChangeWeight: (String, Float?) -> Unit,
    onChangeReps: (String, Int?) -> Unit,
) {
    val pagerState = rememberPagerState(initialExerciseIndex)
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        ExercisesTabRow(
            exerciseProgresses = exercises,
            currentPage = pagerState.currentPage,
            onSelectPage = { page ->
                scope.launch {
                    pagerState.animateScrollToPage(page)
                }
            }
        )
        ExercisesPager(
            exerciseProgresses = exercises,
            pagerState = pagerState,
            onChangeWeight = onChangeWeight,
            onChangeReps = onChangeReps,
        )
    }
}

@Composable
private fun ExercisesTabRow(
    exerciseProgresses: List<ExerciseProgress>,
    currentPage: Int,
    onSelectPage: (Int) -> Unit,
) {
    TabRow(selectedTabIndex = currentPage) {
        exerciseProgresses.indices.forEach { index ->
            Tab(
                selected = index == currentPage,
                text = {
                    Text(text = (index + 1).toString())
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
    exerciseProgresses: List<ExerciseProgress>,
    pagerState: PagerState,
    onChangeWeight: (String, Float?) -> Unit,
    onChangeReps: (String, Int?) -> Unit,
) {
    HorizontalPager(pageCount = exerciseProgresses.size, state = pagerState) { page ->
        val exercise = exerciseProgresses[page]
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ExerciseView(
                name = exercise.name,
                numOfSets = exercise.sets.total,
                repsRange = exercise.repsRange,
            )
            ExerciseSetsView(
                sets = exercise.setsProgress,
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
    sets: List<SetProgress>,
    onChangeWeight: (String, Float?) -> Unit,
    onChangeReps: (String, Int?) -> Unit
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
        EditExerciseUiState.Loading,
        EditExerciseUiState.Success(
            training = sampleCompleteJournal.weeksProgress.first().trainingsProgress.first(),
            initialExercise = sampleCompleteJournal.weeksProgress.first().trainingsProgress.first().exercisesProgress[2],
        ),
        EditExerciseUiState.Success(
            training = sampleEmptyJournal.weeksProgress.first().trainingsProgress.first(),
            initialExercise = sampleEmptyJournal.weeksProgress.first().trainingsProgress.first().exercisesProgress[2],
        ),
    )
}