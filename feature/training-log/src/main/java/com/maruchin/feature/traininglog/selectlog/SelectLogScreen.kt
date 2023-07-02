package com.maruchin.feature.traininglog.selectlog

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.BackButton
import com.maruchin.core.ui.ContentPlaceholder
import com.maruchin.core.ui.GymsterTheme
import com.maruchin.data.training.Log
import com.maruchin.data.training.samplePlan
import com.maruchin.feature.activelog.R

@Composable
internal fun SelectLogScreen(
    state: SelectLogUiState,
    onBack: () -> Unit,
    onSelectLog: (Log) -> Unit,
    onAddNewLog: () -> Unit,
    onDeleteLog: (Log) -> Unit,
) {
    var logToDelete by remember { mutableStateOf<Log?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(onBack = onBack, onAddNewLog = onAddNewLog)
        }
    ) { padding ->
        if (state.savedLogs.isEmpty()) {
            ContentPlaceholder(
                icon = Icons.Default.FitnessCenter,
                text = stringResource(R.string.logs_placeholder),
                modifier = Modifier.padding(padding)
            )
        } else {
            LogListView(
                logs = state.savedLogs,
                onSelectLog = onSelectLog,
                onDeleteLog = { logToDelete = it },
                modifier = Modifier.padding(padding),
            )
        }
    }
    logToDelete?.let { log ->
        DeleteLogDialog(
            logName = log.name,
            onDismiss = { logToDelete = null },
            onConfirm = { onDeleteLog(log); logToDelete = null }
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
    logs: List<Log>,
    modifier: Modifier = Modifier,
    onSelectLog: (Log) -> Unit,
    onDeleteLog: (Log) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(logs) { log ->
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

@Preview
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
        SelectLogUiState(
            savedLogs = listOf(
                Log(
                    name = "Mój pierwszy plan",
                    plan = samplePlan,
                ),
                Log(
                    name = "Mój drugi plan",
                    plan = samplePlan,
                ),
                Log(
                    name = "Mój trzeci plan",
                    plan = samplePlan,
                ),
            )
        )
    )
}