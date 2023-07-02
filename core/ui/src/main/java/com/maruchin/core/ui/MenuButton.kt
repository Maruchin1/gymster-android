package com.maruchin.core.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MenuButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = stringResource(R.string.menu))
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuButtonPreview() {
    GymsterTheme {
        MenuButton(onClick = { /*TODO*/ })
    }
}