package com.maruchin.feature.traininglog.activelog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.model.ID
import com.maruchin.core.ui.ContentPlaceholder
import com.maruchin.core.ui.GymsterTheme
import com.maruchin.core.ui.VerticalDivider
import com.maruchin.data.training.Exercise
import com.maruchin.data.training.Log
import com.maruchin.data.training.ExerciseSet
import com.maruchin.data.training.TrainingDay
import com.maruchin.data.training.TrainingWeek
import com.maruchin.data.training.samplePlan
import com.maruchin.feature.activelog.R
import kotlinx.coroutines.launch

@Composable
internal fun ActiveLogScreen(
    state: ActiveLogUiState,
    onSelectLog: () -> Unit,
    onEditExerciseSet: (ExerciseSet) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(logName = state.log?.name, onSelectLog = onSelectLog)
        }
    ) { padding ->
        if (state.log == null) {
            ContentPlaceholder(
                icon = Icons.Default.FitnessCenter,
                text = stringResource(R.string.logs_placeholder),
                modifier = Modifier.padding(padding)
            )
        } else {
            WeeksView(
                weeks = state.log.weeks,
                onEditExerciseSet = onEditExerciseSet,
                modifier = Modifier.padding(padding)
            )
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
        }
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
    weeks: List<TrainingWeek>,
    onEditExerciseSet: (ExerciseSet) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.then(modifier)) {
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
            onEditExerciseSet = onEditExerciseSet,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun WeeksTabRow(weeks: List<TrainingWeek>, currentPage: Int, onChangePage: (Int) -> Unit) {
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
    weeks: List<TrainingWeek>,
    pagerState: PagerState,
    onEditExerciseSet: (ExerciseSet) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        pageCount = weeks.size,
        state = pagerState,
        modifier = modifier
    ) { page ->
        val week = weeks[page]
        TrainingWeekView(days = week.days, onEditExerciseSet = onEditExerciseSet)
    }
}

@Composable
private fun TrainingWeekView(days: List<TrainingDay>, onEditExerciseSet: (ExerciseSet) -> Unit) {
    Column {
        days.forEach { day ->
            TrainingDayView(
                name = day.name,
                exercises = day.exercises,
                onEditExerciseSet = onEditExerciseSet,
            )
        }
        Spacer(modifier = Modifier.height(128.dp))
    }
}

@Composable
private fun ColumnScope.TrainingDayView(
    name: String,
    exercises: List<Exercise>,
    onEditExerciseSet: (ExerciseSet) -> Unit,
) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
    exercises.forEach { exercise ->
        ExerciseView(
            number = exercise.number,
            name = exercise.name,
            repsRange = exercise.repsRange,
            exerciseSets = exercise.exerciseSets,
            onEditExerciseSet = onEditExerciseSet,
        )
    }
}

@Composable
private fun ColumnScope.ExerciseView(
    number: String,
    name: String,
    repsRange: IntRange,
    exerciseSets: List<ExerciseSet>,
    onEditExerciseSet: (ExerciseSet) -> Unit,
) {
    Row(modifier = Modifier.padding(16.dp)) {
        Text(
            text = number,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = repsRange.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp)
    ) {
        VerticalDivider()
        exerciseSets.forEach { set ->
            Text(
                text = set.weight?.let { weight ->
                    set.reps?.let { reps ->
                        "$weight x $reps"
                    }
                } ?: "--",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onEditExerciseSet(set) }
                    .padding(16.dp)
            )
            VerticalDivider()
        }
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}


@Preview
@Composable
private fun ActiveLogScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: ActiveLogUiState
) {
    GymsterTheme {
        ActiveLogScreen(
            state = state,
            onSelectLog = {},
            onEditExerciseSet = {}
        )
    }
}

class UiStateProvider : PreviewParameterProvider<ActiveLogUiState> {
    override val values = sequenceOf(
        ActiveLogUiState(),
        ActiveLogUiState(
            log = Log(name = "Q1 2023", plan = samplePlan)
        )
    )
}