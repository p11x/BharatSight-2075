package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import com.bharatsight2075.ui.theme.RetroDarkColors
import androidx.compose.material3.Text

@Preview(showBackground = true, widthDp = 100, heightDp = 100)
@Composable
fun MicroTelemetryGaugePreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MicroTelemetryGauge Test",
            color = RetroDarkColors.TextPrimary
        )
        
         Row(
             modifier = Modifier
                 .fillMaxWidth(),
             horizontalArrangement = Arrangement.spacedBy(16.dp)
         ) {
            MicroTelemetryGauge(
                percentage = -40f,
                color = RetroDarkColors.NeonGreen,
                modifier = Modifier.size(48.dp)
            )
            
            MicroTelemetryGauge(
                percentage = 0f,
                color = RetroDarkColors.NeonCyan,
                modifier = Modifier.size(48.dp)
            )
            
            MicroTelemetryGauge(
                percentage = 60f,
                color = RetroDarkColors.NeonOrange,
                modifier = Modifier.size(48.dp)
            )
            
            MicroTelemetryGauge(
                percentage = 100f,
                color = RetroDarkColors.NeonMagenta,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}