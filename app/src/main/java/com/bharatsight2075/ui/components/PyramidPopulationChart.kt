package com.bharatsight2075.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun PyramidPopulationChart(
    modifier: Modifier = Modifier.fillMaxWidth().height(400.dp)
) {
    var year by remember { mutableStateOf(2025f) }
    
    Column(modifier = modifier) {
        Text(
            "POPULATION PYRAMID: ${year.toInt()}",
            color = RetroDarkColors.NeonCyan,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Canvas(modifier = Modifier.weight(1f).fillMaxWidth()) {
            val center = size.width / 2
            val barHeight = size.height / 20
            
            for (i in 0 until 20) {
                val ageGroup = i * 5
                // Simulated data shift based on year
                val maleWidth = (100f - ageGroup) * (1 + (year - 2025) / 100f) * (size.width / 300f)
                val femaleWidth = (95f - ageGroup) * (1 + (year - 2025) / 110f) * (size.width / 300f)
                
                // Male (Left)
                drawRect(
                    color = RetroDarkColors.NeonCyan.copy(alpha = 0.7f),
                    topLeft = Offset(center - maleWidth, i * barHeight),
                    size = Size(maleWidth, barHeight - 2f)
                )
                
                // Female (Right)
                drawRect(
                    color = RetroDarkColors.NeonMagenta.copy(alpha = 0.7f),
                    topLeft = Offset(center, i * barHeight),
                    size = Size(femaleWidth, barHeight - 2f)
                )
            }
        }
        
        Slider(
            value = year,
            onValueChange = { year = it },
            valueRange = 2001f..2075f,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
