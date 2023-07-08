package com.maruchin.feature.traininglogs.activelog

import androidx.compose.animation.Crossfade
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.model.ID
import com.maruchin.core.ui.content.EmptyContentView
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.content.LoadingContentView
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.training.model.JournalExercise
import com.maruchin.data.training.model.JournalSet
import com.maruchin.data.training.model.JournalDay
import com.maruchin.data.training.model.JournalWeek
import com.maruchin.data.training.model.sampleCompleteJournal
import com.maruchin.data.training.model.sampleEmptyJournal
import com.maruchin.feature.traininglogs.R
import kotlinx.coroutines.launch

@Composable
internal fun ActiveLogScreen(
    state: ActiveLogUiState,
    onSelectLog: () -> Unit,
    onChangeCurrentWeek: (JournalWeek) -> Unit,
    onEditExercise: (JournalDay, JournalExercise) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                logName = state.logName,
                onSelectLog = onSelectLog
            )
        }
    ) { padding ->
        Crossfade(state.status, modifier = Modifier.padding(padding)) { status ->
            when (status) {
                ActiveLogUiState.Status.LOADING -> LoadingContentView()

                ActiveLogUiState.Status.NO_ACTIVE_LOG -> EmptyContentView(
                    icon = Icons.Default.FitnessCenter,
                    text = stringResource(R.string.active_log_placeholder)
                )

                ActiveLogUiState.Status.LOADED -> WeeksView(
                    weeks = state.weeks,
                    currentWeekId = state.currentWeekId,
                    onChangeCurrentWeek = onChangeCurrentWeek,
                    onEditExercise = onEditExercise,
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(logName: String?, onSelectLog: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = logName ?: stringResource(R.string.training_log))
        },
        actions = {
            SelectLogButton(onClick = onSelectLog)
        },
    )
}

@Composable
private fun SelectLogButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.List,
            contentDescription = stringResource(R.string.select_log),
        )
    }
}

@Composable
private fun WeeksView(
    weeks: List<JournalWeek>,
    currentWeekId: ID,
    onChangeCurrentWeek: (JournalWeek) -> Unit,
    onEditExercise: (JournalDay, JournalExercise) -> Unit,
) {
    val currentPage = weeks.indexOfFirst { it.id == currentWeekId }
    val pagerState = rememberPagerState(currentPage)
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val week = weeks[page]
            onChangeCurrentWeek(week)
        }
    }
    Column {
        WeeksTabRow(
            weeks = weeks,
            currentPage = pagerState.currentPage,
            onChangePage = { page ->
                scope.launch { pagerState.animateScrollToPage(page) }
            },
        )
        WeeksPager(
            weeks = weeks,
            pagerState = pagerState,
            onEditExercise = onEditExercise,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun WeeksTabRow(weeks: List<JournalWeek>, currentPage: Int, onChangePage: (Int) -> Unit) {
    ScrollableTabRow(selectedTabIndex = currentPage) {
        weeks.forEachIndexed { index, week ->
            WeekTab(
                weekNumber = week.number,
                selected = index == currentPage,
                onClick = { onChangePage(index) },
            )
        }
    }
}

@Composable
private fun WeekTab(weekNumber: Int, selected: Boolean, onClick: () -> Unit) {
    Tab(
        selected = selected,
        onClick = onClick,
        text = {
            Text(text = "${stringResource(R.string.week)} $weekNumber")
        },
    )
}

@Composable
private fun WeeksPager(
    weeks: List<JournalWeek>,
    pagerState: PagerState,
    onEditExercise: (JournalDay, JournalExercise) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        pageCount = weeks.size,
        state = pagerState,
        modifier = modifier
    ) { page ->
        val week = weeks[page]
        TrainingWeekView(days = week.days, onEditExercise = onEditExercise)
    }
}

@Composable
private fun TrainingWeekView(
    days: List<JournalDay>,
    onEditExercise: (JournalDay, JournalExercise) -> Unit
) {
    Column {
        days.forEach { day ->
            TrainingDayView(
                name = day.name,
                journalExercises = day.exercises,
                onEditExercise = { onEditExercise(day, it) },
            )
        }
        Spacer(modifier = Modifier.height(128.dp))
    }
}

@Composable
private fun TrainingDayView(
    name: String,
    journalExercises: List<JournalExercise>,
    onEditExercise: (JournalExercise) -> Unit,
) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    )
    journalExercises.forEach { exercise ->
        ExerciseView(
            name = exercise.name,
            repsRange = exercise.repsRange,
            journalSets = exercise.sets,
            onEdit = { onEditExercise(exercise) }
        )
    }
}

@Composable
private fun ExerciseView(
    name: String,
    repsRange: IntRange,
    journalSets: List<JournalSet>,
    onEdit: () -> Unit
) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        OutlinedCard(
            modifier = Modifier
                .weight(3f)
                .padding(8.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 2,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp, bottom = 4.dp)
            )
            Text(
                text = "${journalSets.size} serie",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
            Text(
                text = "$repsRange powtórzeń",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 4.dp, bottom = 12.dp)
            )
        }
        Card(
            onClick = onEdit,
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
                .padding(end = 8.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            (0..2).forEach { index ->
                val set = journalSets.getOrNull(index)
                ExerciseSetView(
                    weight = set?.weight,
                    reps = set?.reps,
                    visible = set != null,
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ExerciseSetView(weight: Float?, reps: Int?, visible: Boolean) {
    if (visible) {
        Row {
            Text(
                text = weight?.toString() ?: "--",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "X",
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
    } else {
        Text(text = "", style = MaterialTheme.typography.labelLarge)
    }
}

@LightAndDarkPreview
@Composable
private fun ActiveLogScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: ActiveLogUiState
) {
    GymsterTheme {
        ActiveLogScreen(
            state = state,
            onSelectLog = {},
            onChangeCurrentWeek = {},
            onEditExercise = { _, _ -> },
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<ActiveLogUiState> {
    override val values = sequenceOf(
        ActiveLogUiState(),
        ActiveLogUiState(null),
        ActiveLogUiState(sampleEmptyJournal),
        ActiveLogUiState(sampleCompleteJournal)
    )
}