package com.bharatsight2075.ui.visualization.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import com.bharatsight2075.ui.theme.RetroDarkColors

data class PillarData(
    val label: String,
    val value: Float, // 0..1
    val color: Color
)

@Composable
fun IsometricPillarChart(
    modifier: Modifier = Modifier,
    pillars: List<PillarData>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize().clipToBounds()) {
            val count = pillars.size
            val spacing = size.width / (count + 1)
            val pillarWidth = spacing * 0.5f
            val isoSkew = pillarWidth * 0.4f // Flattening factor
            
            pillars.forEachIndexed { index, pillar ->
                val centerX = spacing * (index + 1)
                val maxHeight = size.height * 0.7f
                val h = pillar.value * maxHeight
                val baseLine = size.height - 20.dp.toPx()
                
                // --- 3D MATH: ISOMETRIC PROJECTION ---
                // Front face bottom-left: (centerX - pillarWidth/2, baseLine)
                
                val leftX = centerX - pillarWidth / 2
                val rightX = centerX + pillarWidth / 2
                val topY = baseLine - h
                
                // 1. LEFT FACE (Medium Shade)
                val leftPath = Path().apply {
                    moveTo(leftX, baseLine)
                    lineTo(leftX, topY)
                    lineTo(centerX, topY + isoSkew)
                    lineTo(centerX, baseLine + isoSkew)
                    close()
                }
                drawPath(leftPath, pillar.color.copy(alpha = 0.7f))
                
                // 2. RIGHT FACE (Dark Shade)
                val rightPath = Path().apply {
                    moveTo(centerX, baseLine + isoSkew)
                    lineTo(centerX, topY + isoSkew)
                    lineTo(rightX, topY)
                    lineTo(rightX, baseLine)
                    close()
                }
                drawPath(rightPath, pillar.color.copy(alpha = 0.4f))
                
                // 3. TOP FACE (Light Shade / Highlight)
                val topPath = Path().apply {
                    moveTo(leftX, topY)
                    lineTo(centerX - isoSkew, topY - isoSkew)
                    lineTo(centerX + (pillarWidth - isoSkew), topY - isoSkew)
                    lineTo(rightX, topY)
                    close()
                }
                // Recalculating Top slightly for better diamond shape
                val diamondPath = Path().apply {
                    moveTo(centerX, topY + isoSkew)
                    lineTo(leftX, topY)
                    lineTo(centerX, topY - isoSkew)
                    lineTo(rightX, topY)
                    close()
                }
                drawPath(diamondPath, pillar.color.copy(alpha = 0.9f))
            }
        }
        
        // Labels Row
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            pillars.forEach { pillar ->
                Text(
                    text = pillar.label,
                    color = RetroDarkColors.TextSecondary,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
