package com.ostapr.focusapp.feature.status.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ostapr.focusapp.core.designsystem.theme.FocusAppTheme

@Composable
fun StatusesListScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text("Hello")
    }
}

@Preview()
@Composable
fun StatusesListScreenPreview() {
    FocusAppTheme {
        StatusesListScreen()
    }
}