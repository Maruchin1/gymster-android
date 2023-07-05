package com.maruchin.feature.traininglog.selectlog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.maruchin.core.ui.theme.GymsterTheme

@Composable
internal fun DeleteLogDialog(
    logName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Usuń dziennik")
        },
        text = {
            Text(text = logName)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Usuń")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Zamknij")
            }
        }
    )
}

@Preview
@Composable
private fun ConfirmLogDeletionDialogPreview() {
    GymsterTheme {
        DeleteLogDialog(
            logName = "Dziennik 1",
            onDismiss = {},
            onConfirm = {}
        )
    }
}