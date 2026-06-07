package com.bharatsight2075.ui.visualization.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun DottedParticleWorldMap(
    modifier: Modifier = Modifier,
    dotColor: Color = RetroDarkColors.NeonGreen.copy(alpha = 0.5f)
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val rows = 30
        val cols = 60
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows
        
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                // Approximate world map mask logic
                val isLand = (r in 5..25 && c in 10..50) && ((r + c) % 2 == 0)
                if (isLand) {
                    drawCircle(
                        color = dotColor,
                        radius = 1.5.dp.toPx(),
                        center = Offset(c * cellWidth, r * cellHeight)
                    )
                }
            }
        }
    }
}
