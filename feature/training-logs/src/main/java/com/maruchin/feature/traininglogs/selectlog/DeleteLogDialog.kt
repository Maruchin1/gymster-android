package com.maruchin.feature.traininglogs.selectlog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.feature.traininglogs.R

@Composable
internal fun DeleteLogDialog(
    logName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.delete_log))
        },
        text = {
            Text(text = logName)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.close))
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