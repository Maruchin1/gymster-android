package com.maruchin.feature.trainingplans.planlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.content.EmptyContentView
import com.maruchin.core.ui.content.LoadingContentView
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.model.PlanDay
import com.maruchin.data.plan.model.samplePlan
import com.maruchin.feature.trainingplans.R
import kotlinx.coroutines.launch

@Composable
internal fun PlanListScreen(state: PlanListUiState, onCreatePlan: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(onCreatePlan = onCreatePlan)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                PlanListUiState.Loading -> LoadingContentView()

                PlanListUiState.NoPlans -> EmptyContentView(
                    icon = Icons.Default.List,
                    text = stringResource(R.string.empty_plans)
                )

                is PlanListUiState.Loaded -> PlanListContent(state.plans)
            }
        }
    }
}

@Composable
private fun TopAppBar(onCreatePlan: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.training_plans))
        },
        actions = {
            IconButton(onClick = onCreatePlan) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}

@Composable
private fun PlanListContent(trainingPlans: List<Plan>) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        TrainingPlanTabRow(
            trainingPlans = trainingPlans,
            currentPage = pagerState.currentPage,
            onChangePage = { page ->
                scope.launch {
                    pagerState.animateScrollToPage(page)
                }
            }
        )
        TrainingPlanPager(trainingPlans = trainingPlans, pagerState = pagerState)
    }
}

@Composable
private fun TrainingPlanTabRow(
    trainingPlans: List<Plan>,
    currentPage: Int,
    onChangePage: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier.fillMaxWidth()
    ) {
        trainingPlans.forEachIndexed { index, trainingPlan ->
            Tab(
                selected = index == currentPage,
                text = {
                    Text(text = trainingPlan.name)
                },
                onClick = {
                    onChangePage(index)
                }
            )
        }
    }
}

@Composable
private fun TrainingPlanPager(trainingPlans: List<Plan>, pagerState: PagerState) {
    HorizontalPager(pageCount = trainingPlans.size, state = pagerState) { page ->
        val trainingPlan = trainingPlans[page]
        TrainingPlanView(days = trainingPlan.days)
    }
}

@Composable
private fun TrainingPlanView(days: List<PlanDay>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        days.forEach { trainingDay ->
            item {
                Text(
                    text = trainingDay.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 24.dp
                    )
                )
            }
            items(trainingDay.exercises) { exercise ->
                ExerciseView(
                    name = exercise.name,
                    numOfSets = exercise.sets,
                    repsRange = exercise.repsRange,
                )
            }
        }
    }
}

@Composable
private fun ExerciseView(name: String, numOfSets: Int, repsRange: IntRange) {
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = "$numOfSets ${stringResource(R.string.sets)}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Text(
            text = "$repsRange ${stringResource(R.string.reps)}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@LightAndDarkPreview
@Composable
private fun PlanListScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: PlanListUiState
) {
    GymsterTheme {
        PlanListScreen(
            state = state,
            onCreatePlan = {}
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<PlanListUiState> {
    override val values = sequenceOf(
        PlanListUiState.Loading,
        PlanListUiState.NoPlans,
        PlanListUiState.Loaded(plans = listOf(samplePlan, samplePlan, samplePlan))
    )
}
