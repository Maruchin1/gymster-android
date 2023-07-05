package com.maruchin.feature.planlist.planlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.feature.planlist.R

@Composable
internal fun CreateNewLogDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var logName by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.create_new_log))
        },
        text = {
            OutlinedTextField(
                value = logName,
                onValueChange = { logName = it },
                placeholder = {
                    Text(text = stringResource(R.string.log_name))
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(logName) }
            ) {
                Text(text = stringResource(R.string.confirm))
            }
        }
    )
}

@Preview
@Composable
private fun CreateNewLogDialogPreview() {
    GymsterTheme {
        CreateNewLogDialog(onDismiss = {}, onConfirm = {})
    }
}