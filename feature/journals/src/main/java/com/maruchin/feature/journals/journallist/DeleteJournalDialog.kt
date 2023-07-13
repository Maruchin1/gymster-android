package com.maruchin.feature.journals.journallist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.maruchin.core.ui.LightAndDarkPreview
import com.maruchin.core.ui.theme.GymsterTheme

@Composable
internal fun DeleteJournalDialog(onClose: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        icon = {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        },
        title = {
            Text(text = "Usuń dziennik")
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(); onClose() }) {
                Text(text = "Usuń")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(text = "Anuluj")
            }
        }
    )
}

@LightAndDarkPreview
@Composable
private fun DeleteJournalDialogPreview() {
    GymsterTheme {
        DeleteJournalDialog(onClose = {}, onConfirm = {})
    }
}