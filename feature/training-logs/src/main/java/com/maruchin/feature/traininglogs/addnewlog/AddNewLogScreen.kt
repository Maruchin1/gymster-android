package com.maruchin.feature.traininglogs.addnewlog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.maruchin.core.model.ID
import com.maruchin.core.ui.BackButton
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.model.samplePlan
import com.maruchin.feature.traininglogs.R

@Composable
internal fun AddNewLogScreen(
    state: AddNewLogUiState,
    onBack: () -> Unit,
    onChangeLogName: (String) -> Unit,
    onSelectPlan: (ID) -> Unit,
    onCreateLog: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(onBack = onBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SectionTitle(text = stringResource(R.string.name_your_new_log))
            LogNameField(value = state.logName, onValueChange = onChangeLogName)
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(text = stringResource(R.string.select_training_plan))
            SelectPlanView(
                trainingPlans = state.plans,
                selectedPlanId = state.selectedPlanId,
                onSelectPlan = onSelectPlan,
            )
            Spacer(modifier = Modifier.weight(1f))
            CreateLogButton(onClick = onCreateLog)
        }
    }
}

@Composable
private fun TopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.new_log))
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        }
    )
}

@Composable
private fun LogNameField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        placeholder = {
            Text(text = stringResource(R.string.log_name))
        },
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun SelectPlanView(
    trainingPlans: List<Plan>,
    selectedPlanId: ID?,
    onSelectPlan: (ID) -> Unit
) {
    Column(modifier = Modifier.selectableGroup()) {
        trainingPlans.forEach { plan ->
            PlanRadioButton(
                name = plan.name,
                selected = plan.id == selectedPlanId,
                onClick = { onSelectPlan(plan.id) }
            )
        }
    }
}

@Composable
private fun PlanRadioButton(name: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .selectable(
                role = Role.RadioButton,
                selected = selected,
                onClick = onClick,
            )
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun CreateLogButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.create_log))
    }
}

@LightAndDarkPreview
@Composable
private fun AddNewLogScreenPreview() {
    GymsterTheme {
        AddNewLogScreen(
            state = AddNewLogUiState(
                plans = listOf(samplePlan, samplePlan, samplePlan)
            ),
            onBack = {},
            onChangeLogName = {},
            onSelectPlan = {},
            onCreateLog = {},
        )
    }
}