package com.maruchin.feature.journals.activejournal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.ExerciseItem
import com.maruchin.core.ui.content.EmptyContent
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.TrainingItem
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.plan.model.Sets
import com.maruchin.data.training.model.ExerciseProgress
import com.maruchin.data.training.model.SetProgress
import com.maruchin.data.training.model.TrainingProgress
import com.maruchin.data.training.model.WeekProgress
import com.maruchin.data.training.model.sampleCompleteJournal
import com.maruchin.data.training.model.sampleEmptyJournal
import com.maruchin.feature.journals.R
import kotlinx.coroutines.launch

@Composable
internal fun ActiveJournalScreen(
    state: ActiveJournalUiState,
    onSelectLog: () -> Unit,
    onEditExercise: (TrainingProgress, ExerciseProgress) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                journalName = when (state) {
                    is ActiveJournalUiState.Success -> state.journal.fullName
                    else -> ""
                },
                onSelectJournal = onSelectLog
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                ActiveJournalUiState.Loading -> LoadingContent()

                ActiveJournalUiState.NoJournal -> EmptyContent(
                    icon = Icons.Default.FitnessCenter,
                    text = stringResource(R.string.active_log_placeholder)
                )

                is ActiveJournalUiState.Success -> ActiveJournalContent(
                    weeks = state.journal.weeksProgress,
                    onEditExercise = onEditExercise
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(journalName: String, onSelectJournal: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = journalName)
        },
        actions = {
            IconButton(onClick = onSelectJournal) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = stringResource(R.string.select_log),
                )
            }
        },
    )
}

@Composable
private fun ActiveJournalContent(
    weeks: List<WeekProgress>,
    onEditExercise: (TrainingProgress, ExerciseProgress) -> Unit,
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column {
        WeeksTabRow(
            weeks = weeks,
            currentPage = pagerState.currentPage,
            onChangePage = { page ->
                scope.launch { pagerState.animateScrollToPage(page) }
            },
        )
        WeeksProgressPager(
            weeks = weeks,
            pagerState = pagerState,
            onEditExercise = onEditExercise,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun WeeksTabRow(weeks: List<WeekProgress>, currentPage: Int, onChangePage: (Int) -> Unit) {
    ScrollableTabRow(selectedTabIndex = currentPage) {
        weeks.indices.forEach { page ->
            Tab(
                selected = page == currentPage,
                onClick = { onChangePage(page) },
                text = {
                    Text(text = "${stringResource(R.string.week)} ${page + 1}")
                },
            )
        }
    }
}

@Composable
private fun WeeksProgressPager(
    weeks: List<WeekProgress>,
    pagerState: PagerState,
    onEditExercise: (TrainingProgress, ExerciseProgress) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        pageCount = weeks.size,
        state = pagerState,
        modifier = modifier
    ) { page ->
        val week = weeks[page]
        WeekProgressPage(days = week.trainingsProgress, onEditExercise = onEditExercise)
    }
}

@Composable
private fun WeekProgressPage(
    days: List<TrainingProgress>,
    onEditExercise: (TrainingProgress, ExerciseProgress) -> Unit
) {
    Column {
        days.forEach { day ->
            TrainingProgressItem(
                name = day.name,
                exerciseProgresses = day.exercisesProgress,
                onEditExercise = { onEditExercise(day, it) },
            )
        }
        Spacer(modifier = Modifier.height(128.dp))
    }
}

@Composable
private fun TrainingProgressItem(
    name: String,
    exerciseProgresses: List<ExerciseProgress>,
    onEditExercise: (ExerciseProgress) -> Unit,
) {
    TrainingItem(name = name)
    exerciseProgresses.forEach { exercise ->
        ExerciseProgressItem(
            name = exercise.name,
            sets = exercise.sets,
            repsRange = exercise.repsRange,
            rest = exercise.rest,
            setsProgress = exercise.setsProgress,
            onEdit = { onEditExercise(exercise) }
        )
    }
}

@Composable
private fun ExerciseProgressItem(
    name: String,
    sets: Sets,
    repsRange: IntRange,
    rest: String,
    setsProgress: List<SetProgress>,
    onEdit: () -> Unit
) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        ExerciseItem(
            name = name,
            sets = sets.toString(),
            repsRange = repsRange.toString(),
            rest = rest,
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
        )
        Card(
            onClick = onEdit,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
                .padding(end = 8.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            setsProgress.forEach { set ->
                SetProgressItem(weight = set.weight, reps = set.reps)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SetProgressItem(weight: Float?, reps: Int?) {
    Row {
        Text(
            text = weight?.toString() ?: "--",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "x",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
        )
        Text(
            text = reps?.toString() ?: "--",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}

@LightAndDarkPreview
@Composable
private fun ActiveLogScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: ActiveJournalUiState
) {
    GymsterTheme {
        ActiveJournalScreen(
            state = state,
            onSelectLog = {},
            onEditExercise = { _, _ -> },
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<ActiveJournalUiState> {
    override val values = sequenceOf(
        ActiveJournalUiState.Loading,
        ActiveJournalUiState.NoJournal,
        ActiveJournalUiState.Success(sampleEmptyJournal),
        ActiveJournalUiState.Success(sampleCompleteJournal)
    )
}