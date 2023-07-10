package com.maruchin.core.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.theme.GymsterTheme

@Composable
fun DiscardChangesDialog(onCancel: () -> Unit, onDiscard: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        text = {
            Text(text = "Odzucić zmiany?")
        },
        confirmButton = {
            TextButton(onClick = onDiscard) {
                Text(text = "Odrzuć")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "Anuluj")
            }
        }
    )
}

@LightAndDarkPreview
@Composable
private fun DiscardChangesDialog() {
    GymsterTheme {
        DiscardChangesDialog(onCancel = {}, onDiscard = {})
    }
}