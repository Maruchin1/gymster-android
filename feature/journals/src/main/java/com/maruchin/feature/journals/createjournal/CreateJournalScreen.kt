package com.maruchin.feature.journals.createjournal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.model.samplePlan
import com.maruchin.feature.journals.R

@Composable
internal fun CreateJournalScreen(
    state: CreateJournalUiState,
    onClose: () -> Unit,
    onSave: () -> Unit,
    onChangeName: (String) -> Unit,
    onSelectPlan: (Plan) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(onClose = onClose, onSave = onSave)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = onChangeName,
                label = {
                    Text(text = "Nazwij swój dziennik")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = "Wybierz plan treningowy",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            state.plans.forEach { plan ->
                FilterChip(
                    selected = plan == state.selectedPlan,
                    onClick = { onSelectPlan(plan) },
                    label = {
                        Text(text = plan.name)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(onClose: () -> Unit, onSave: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.create_journal))
        },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        actions = {
            TextButton(onClick = onSave) {
                Text(text = "Zapisz")
            }
        }
    )
}

@LightAndDarkPreview
@Composable
private fun AddNewLogScreenPreview() {
    GymsterTheme {
        CreateJournalScreen(
            state = CreateJournalUiState(
                plans = listOf(samplePlan, samplePlan, samplePlan),
                selectedPlan = samplePlan
            ),
            onClose = {},
            onSave = {},
            onChangeName = {},
            onSelectPlan = {},
        )
    }
}