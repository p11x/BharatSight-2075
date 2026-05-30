package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun TelemetryDrawerPreview() {
    // Mock navigation callback
    val mockNavigate: (String) -> Unit = { route ->
        // In a real app, this would navigate to the screen
        println("Navigating to: $route")
    }
    
    TelemetryDrawer(
        selectedRoute = "overview",
        onNavigate = mockNavigate
    )
}