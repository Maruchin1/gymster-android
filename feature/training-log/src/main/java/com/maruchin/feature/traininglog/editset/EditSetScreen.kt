package com.maruchin.feature.traininglog.editset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.BackButton
import com.maruchin.core.ui.GymsterTheme
import com.maruchin.core.ui.VerticalDivider

@Composable
internal fun EditSetScreen(
    state: EditSetUiState,
    onBack: () -> Unit,
    onChangeWeight: (String) -> Unit,
    onChangeReps: (String) -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edytuj ")
                },
                navigationIcon = {
                    BackButton(onClick = onBack)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    EditableValue(
                        label = "Ciężar",
                        value = state.weightInput,
                        onValueChange = onChangeWeight,
                        modifier = Modifier.weight(1f)
                    )
                    EditableValue(
                        label = "Powtórzenia",
                        value = state.repsInput,
                        onValueChange = onChangeReps,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Button(
                onClick = onSave, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Zapisz")
            }
        }
    }
}

@Composable
private fun EditableValue(
    label: String,
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value ?: "",
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier.padding(16.dp),
    )
}

@Preview
@Composable
private fun EditSetScreenPreview(@PreviewParameter(UiStateProvider::class) state: EditSetUiState) {
    GymsterTheme {
        EditSetScreen(
            state = state,
            onBack = {},
            onChangeWeight = {},
            onChangeReps = {},
            onSave = {}
        )
    }
}

private class UiStateProvider : PreviewParameterProvider<EditSetUiState> {
    override val values = sequenceOf(
        EditSetUiState(),
        EditSetUiState(weightInput = "60", repsInput = "10")
    )
}