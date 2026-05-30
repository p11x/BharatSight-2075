package com.bharatsight2075.ui.visualization.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun DottedParticleWorldMap(
    modifier: Modifier = Modifier,
    dotColor: Color = RetroDarkColors.NeonGreen.copy(alpha = 0.5f)
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .clipToBounds()
    ) {
        val rows = 20
        val cols = 40
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows
        
        // Random-ish dotted map pattern
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if ((r + c) % 3 == 0 || (r * c) % 7 == 0) {
                    drawCircle(
                        color = dotColor,
                        radius = 2f,
                        center = Offset(c * cellWidth, r * cellHeight)
                    )
                }
            }
        }
    }
}
