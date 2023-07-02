package com.maruchin.core.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maruchin.core.ui.GymsterTheme

@Composable
fun ContentLoadingView(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentLoadingViewPreview() {
    GymsterTheme {
        ContentLoadingView()
    }
}