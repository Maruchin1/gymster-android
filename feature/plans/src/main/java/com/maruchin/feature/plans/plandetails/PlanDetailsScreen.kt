package com.maruchin.feature.plans.plandetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.content.LoadingContentView
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.plan.model.Sets
import com.maruchin.data.plan.model.Training
import com.maruchin.data.plan.model.Week
import com.maruchin.data.plan.model.samplePlan
import kotlinx.coroutines.launch

@Composable
internal fun PlanDetailsScreen(state: PlanDetailsUiState, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                planName = when (state) {
                    PlanDetailsUiState.Loading -> ""
                    is PlanDetailsUiState.Success -> state.plan.name
                },
                onBack = onBack
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                PlanDetailsUiState.Loading -> LoadingContentView()
                is PlanDetailsUiState.Success -> PlanDetailsContent(weeks = state.plan.weeks)
            }
        }
    }
}

@Composable
private fun TopAppBar(planName: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = planName)
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        }
    )
}

@Composable
private fun PlanDetailsContent(weeks: List<Week>) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        WeeksTabRow(
            weeks = weeks,
            currentPage = pagerState.currentPage,
            onChangePage = { page ->
                scope.launch {
                    pagerState.animateScrollToPage(page)
                }
            }
        )
        WeeksPager(
            weeks = weeks,
            pagerState = pagerState,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun WeeksTabRow(weeks: List<Week>, currentPage: Int, onChangePage: (Int) -> Unit) {
    ScrollableTabRow(selectedTabIndex = currentPage) {
        weeks.indices.forEach { index ->
            Tab(
                selected = index == currentPage,
                onClick = { onChangePage(index) },
                text = {
                    Text(text = "Tydzień ${index + 1}")
                }
            )
        }
    }
}

@Composable
private fun WeeksPager(weeks: List<Week>, pagerState: PagerState, modifier: Modifier = Modifier) {
    HorizontalPager(
        pageCount = weeks.size,
        state = pagerState,
        modifier = modifier
    ) { page ->
        val week = weeks[page]
        WeekPage(trainings = week.trainings)
    }
}

@Composable
private fun WeekPage(trainings: List<Training>) {
    Column {
        trainings.forEach { training ->
            TrainingItem(name = training.name)
            training.exercises.forEach { exercise ->
                PlanItem(
                    name = exercise.name,
                    sets = exercise.sets,
                    repsRange = exercise.repsRange,
                    rest = exercise.rest
                )
            }
        }
    }
}

@Composable
private fun TrainingItem(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(12.dp),
        )
    }
}

@Composable
private fun PlanItem(name: String, sets: Sets, repsRange: IntRange, rest: String) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(12.dp)
        )
        Row {
            Column {
                Text(
                    text = "Serie",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                )
                Text(
                    text = "Powtórzenia",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                )
                Text(
                    text = "Przerwa",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                )
            }
            Column {
                Text(
                    text = sets.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                )
                Text(
                    text = repsRange.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                )
                Text(
                    text = rest,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun PlanDetailsScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: PlanDetailsUiState
) {
    GymsterTheme {
        PlanDetailsScreen(state = state, onBack = {})
    }
}

private class UiStateProvider : PreviewParameterProvider<PlanDetailsUiState> {
    override val values = sequenceOf(
        PlanDetailsUiState.Loading,
        PlanDetailsUiState.Success(plan = samplePlan)
    )
}