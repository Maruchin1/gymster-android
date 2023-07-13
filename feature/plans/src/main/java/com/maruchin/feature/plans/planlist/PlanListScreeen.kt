package com.maruchin.feature.plans.planlist

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
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.model.samplePlan
import com.maruchin.feature.plans.R

@Composable
internal fun PlanListScreen(
    state: PlanListUiState,
    onOpenPlan: (Plan) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                PlanListUiState.Loading -> LoadingContent()

                is PlanListUiState.Success -> PlansGrid(
                    plans = state.plans,
                    onOpenPlan = onOpenPlan
                )
            }
        }
    }
}

@Composable
private fun TopAppBar() {
    TopAppBar(
        title = {
            Text(text = "Plany treningowe")
        }
    )
}

@Composable
private fun PlansGrid(plans: List<Plan>, onOpenPlan: (Plan) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(plans) { plan ->
            PlanItem(
                name = plan.name,
                weeks = plan.weeks.size.toString(),
                onOpen = { onOpenPlan(plan) }
            )
        }
    }
}

@Composable
private fun PlanItem(name: String, weeks: String, onOpen: () -> Unit) {
    OutlinedCard(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.plan_placeholder),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            minLines = 2,
            maxLines = 2,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth(),
        )
        Text(
            text = "$weeks tygodni",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
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
            onOpenPlan = {}
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<PlanListUiState> {
    override val values = sequenceOf(
        PlanListUiState.Loading,
        PlanListUiState.Success(
            plans = listOf(
                samplePlan,
                samplePlan,
                samplePlan,
                samplePlan,
                samplePlan
            )
        )
    )
}
