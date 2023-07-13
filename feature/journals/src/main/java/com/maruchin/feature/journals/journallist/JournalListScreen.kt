package com.maruchin.feature.journals.journallist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.content.LoadingContent
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.training.model.Journal
import com.maruchin.data.training.model.sampleCompleteJournal
import com.maruchin.data.training.model.sampleEmptyJournal

@Composable
internal fun JournalListScreen(
    state: JournalListUiState,
    onBack: () -> Unit,
    onSelectJournal: (Journal) -> Unit,
    onDeleteJournal: (Journal) -> Unit,
    onCreateJournal: () -> Unit,
) {
    var journalToDelete by remember { mutableStateOf<Journal?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Dzienniki treningowe")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                JournalListUiState.Loading -> LoadingContent()

                is JournalListUiState.Success -> JournalListContent(
                    journals = state.journals,
                    onSelectJournal = onSelectJournal,
                    onDeleteJournal = { journalToDelete = it },
                    onCreateJournal = onCreateJournal
                )
            }
        }
    }
    journalToDelete?.let { journal ->
        DeleteJournalDialog(
            onClose = { journalToDelete = null },
            onConfirm = { onDeleteJournal(journal) }
        )
    }
}

@Composable
private fun JournalListContent(
    journals: List<Journal>,
    onSelectJournal: (Journal) -> Unit,
    onDeleteJournal: (Journal) -> Unit,
    onCreateJournal: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(journals) { journal ->
            JournalItem(
                name = journal.fullName,
                onSelect = { onSelectJournal(journal) },
                onDelete = { onDeleteJournal(journal) }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
        item {
            CreateJournalButton(onClick = onCreateJournal)
        }
    }
}

@Composable
private fun JournalItem(name: String, onSelect: () -> Unit, onDelete: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.combinedClickable(
            onClick = onSelect,
            onLongClick = onDelete
        )
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun CreateJournalButton(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = "Nowy dziennik...",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@LightAndDarkPreview
@Composable
private fun JournalListScreenPreview(@PreviewParameter(UiStateProvider::class) state: JournalListUiState) {
    GymsterTheme {
        JournalListScreen(
            state = state,
            onBack = {},
            onCreateJournal = {},
            onDeleteJournal = {},
            onSelectJournal = {}
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<JournalListUiState> {
    override val values = sequenceOf(
        JournalListUiState.Loading,
        JournalListUiState.Success(
            journals = listOf(sampleEmptyJournal, sampleCompleteJournal),
            journalSelected = false,
        )
    )
}