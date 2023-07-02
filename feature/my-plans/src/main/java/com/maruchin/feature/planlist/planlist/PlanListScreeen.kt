package com.maruchin.feature.planlist.planlist

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maruchin.core.model.ID
import com.maruchin.core.ui.GymsterTheme
import com.maruchin.data.training.Exercise
import com.maruchin.data.training.Plan
import com.maruchin.data.training.TrainingDay
import com.maruchin.data.training.samplePlan
import com.maruchin.feature.planlist.R
import kotlinx.coroutines.launch

@Composable
internal fun PlanListScreen(
    state: PlanListUiState,
    onCreateNewLog: (ID, String) -> Unit,
    onCloseMessage: () -> Unit,
) {
    val plansPagerState = rememberPagerState()
    val currentPlan by remember(state.plans) {
        derivedStateOf {
            state.plans[plansPagerState.currentPage]
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var openCreateNewLogDialog by remember { mutableStateOf(false) }

    fun showMessage(message: String) = scope.launch {
        snackbarHostState.showSnackbar(message = message)
        onCloseMessage()
    }

    LaunchedEffect(state.message) {
        if (state.message.isNotEmpty()) {
            showMessage(state.message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar()
        },
        floatingActionButton = {
            CreateNewLogButton(
                onClick = {
                    openCreateNewLogDialog = true
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        PlansPagerView(
            state = plansPagerState,
            plans = state.plans,
            modifier = Modifier.padding(padding)
        )
    }
    if (openCreateNewLogDialog) {
        CreateNewLogDialog(
            onDismiss = {
                openCreateNewLogDialog = false
            },
            onConfirm = { logName ->
                onCreateNewLog(currentPlan.id, logName)
                openCreateNewLogDialog = false
            }
        )
    }
}

@Composable
private fun TopAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.my_plans)) },
    )
}

@Composable
private fun PlansPagerView(state: PagerState, plans: List<Plan>, modifier: Modifier = Modifier) {
    HorizontalPager(
        pageCount = plans.size,
        state = state,
        modifier = modifier.fillMaxSize()
    ) { page ->
        PlanView(plan = plans[page])
    }
}

@Composable
private fun PlanView(plan: Plan) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = plan.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp),
        )
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        plan.days.forEach { day ->
            DayView(day = day)
        }
        Spacer(modifier = Modifier.height(128.dp))
    }
}

@Composable
private fun DayView(day: TrainingDay) {
    Text(
        text = day.name,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
    day.exercises.forEach { exercise ->
        ExerciseView(exercise = exercise)
    }
}

@Composable
private fun ExerciseView(exercise: Exercise) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = "${exercise.exerciseSets.size} x ${exercise.repsRange}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CreateNewLogButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = {
            Text(text = stringResource(R.string.create_new_log))
        },
        icon = {
            Icon(imageVector = Icons.Default.Create, contentDescription = null)
        },
        onClick = onClick,
    )
}

@Preview
@Composable
private fun PlanListScreenPreview() {
    GymsterTheme {
        PlanListScreen(
            state = PlanListUiState(plans = listOf(samplePlan)),
            onCreateNewLog = { _, _ -> },
            onCloseMessage = {},
        )
    }
}
