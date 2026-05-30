package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import com.bharatsight2075.ui.theme.RetroDarkColors
import androidx.compose.material3.Text

@Preview(showBackground = true, widthDp = 100, heightDp = 100)
@Composable
fun MicroSparklinePreview() {
    // Test data
    val testData = listOf(0.1f, 0.3f, 0.7f, 0.5f, 0.9f, 0.2f, 0.6f)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MicroSparkline Test",
            color = RetroDarkColors.TextPrimary
        )
        
        MicroSparkline(
            data = testData,
            color = RetroDarkColors.NeonCyan,
            modifier = Modifier.size(80.dp, 32.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        MicroSparkline(
            data = listOf(0.0f, 0.5f, 1.0f),
            color = RetroDarkColors.NeonOrange,
            modifier = Modifier.size(80.dp, 32.dp)
        )
    }
}