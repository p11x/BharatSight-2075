package com.bharatsight2075.ui.visualization.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

data class PillarData(
    val label: String,
    val value: Float, // 0..1
    val color: Color
)

@Composable
fun IsometricPillarChart(
    modifier: Modifier = Modifier,
    pillars: List<PillarData> = emptyList()
) {
    val safePillars = pillars.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).map { PillarData("M", it as Float / 100f, RetroDarkColors.NeonCyan) }
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        val count = safePillars.size
        val spacing = size.width / (count + 1)
        val pillarWidth = spacing * 0.5f
        val isoSkew = pillarWidth * 0.4f
        
        safePillars.forEachIndexed { index, pillar ->
            val centerX = spacing * (index + 1)
            val maxHeight = size.height * 0.7f
            val h = pillar.value * maxHeight
            val baseLine = size.height - 20.dp.toPx()
            
            val leftX = centerX - pillarWidth / 2
            val rightX = centerX + pillarWidth / 2
            val topY = baseLine - h
            
            val diamondPath = Path().apply {
                moveTo(centerX, topY + isoSkew)
                lineTo(leftX, topY)
                lineTo(centerX, topY - isoSkew)
                lineTo(rightX, topY)
                close()
            }
            
            val leftFace = Path().apply {
                moveTo(leftX, baseLine)
                lineTo(leftX, topY)
                lineTo(centerX, topY + isoSkew)
                lineTo(centerX, baseLine + isoSkew)
                close()
            }
            
            val rightFace = Path().apply {
                moveTo(centerX, baseLine + isoSkew)
                lineTo(centerX, topY + isoSkew)
                lineTo(rightX, topY)
                lineTo(rightX, baseLine)
                close()
            }
            
            drawPath(leftFace, pillar.color.copy(alpha = 0.7f))
            drawPath(rightFace, pillar.color.copy(alpha = 0.4f))
            drawPath(diamondPath, pillar.color)
        }
    }
}
