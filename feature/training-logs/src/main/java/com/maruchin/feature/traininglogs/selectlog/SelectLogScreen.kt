package com.maruchin.feature.traininglogs.selectlog

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.button.BackButton
import com.maruchin.core.ui.content.EmptyContentView
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.content.LoadingContentView
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.plan.model.samplePlan
import com.maruchin.data.training.model.Journal
import com.maruchin.feature.traininglogs.R

@Composable
internal fun SelectLogScreen(
    state: SelectLogUiState,
    onBack: () -> Unit,
    onSelectLog: (Journal) -> Unit,
    onAddNewLog: () -> Unit,
    onDeleteLog: (Journal) -> Unit,
) {
    var journalToDelete by remember { mutableStateOf<Journal?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(onBack = onBack, onAddNewLog = onAddNewLog)
        }
    ) { padding ->
        Crossfade(state.status, modifier = Modifier.padding(padding)) { status ->
            when (status) {
                SelectLogUiState.Status.LOADING -> LoadingContentView()

                SelectLogUiState.Status.NO_SAVED_LOGS -> EmptyContentView(
                    icon = Icons.Default.FitnessCenter,
                    text = stringResource(R.string.active_log_placeholder),
                )

                SelectLogUiState.Status.LOADED -> LogListView(
                    journals = state.savedJournals,
                    onSelectLog = onSelectLog,
                    onDeleteLog = { journalToDelete = it },
                )
            }
        }
    }
    journalToDelete?.let { log ->
        DeleteLogDialog(
            logName = log.name,
            onDismiss = { journalToDelete = null },
            onConfirm = { onDeleteLog(log); journalToDelete = null }
        )
    }
}

@Composable
private fun TopAppBar(onBack: () -> Unit, onAddNewLog: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.training_logs))
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        },
        actions = {
            AddNewLogButton(onClick = onAddNewLog)
        }
    )
}

@Composable
private fun AddNewLogButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
private fun LogListView(
    journals: List<Journal>,
    onSelectLog: (Journal) -> Unit,
    onDeleteLog: (Journal) -> Unit,
) {
    LazyColumn {
        items(journals) { log ->
            MyTrainingLogView(
                name = log.name,
                onClick = { onSelectLog(log) },
                onLongClick = { onDeleteLog(log) }
            )
        }
    }
}

@Composable
private fun MyTrainingLogView(name: String, onClick: () -> Unit, onLongClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}

@LightAndDarkPreview
@Composable
private fun ChangeLogScreenPreview(
    @PreviewParameter(UiStateProvider::class) state: SelectLogUiState
) {
    GymsterTheme {
        SelectLogScreen(
            state = state,
            onBack = {},
            onSelectLog = {},
            onAddNewLog = {},
            onDeleteLog = {},
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<SelectLogUiState> {
    override val values = sequenceOf(
        SelectLogUiState(),
        SelectLogUiState(status = SelectLogUiState.Status.NO_SAVED_LOGS),
        SelectLogUiState(
            listOf(
                Journal(
                    name = "Mój pierwszy plan",
                    plan = samplePlan,
                ),
                Journal(
                    name = "Mój drugi plan",
                    plan = samplePlan,
                ),
                Journal(
                    name = "Mój trzeci plan",
                    plan = samplePlan,
                ),
            )
        )
    )
}