package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

data class MirrorData(val left: Float, val right: Float, val label: String)

/**
 * C22. MirrorBarChart
 * Horizontal bars mirrored left/right.
 */
@Composable
fun MirrorBarChart(
    data: List<MirrorData>,
    leftBrush: Brush = GradPalette.GREEN_TEAL,
    rightBrush: Brush = GradPalette.ORANGE_PINK,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "MirrorAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val barHeight = (height / (data.size * 1.5f))
        val spacing = (height - (barHeight * data.size)) / (data.size + 1)
        
        val maxVal = data.maxOf { maxOf(it.left, it.right) }.coerceAtLeast(0.1f)
        val centerX = width / 2
        
        data.forEachIndexed { index, mirror ->
            val y = spacing + (index * (barHeight + spacing))
            
            // Left Bar
            val leftW = (mirror.left / maxVal) * (width / 2 - 20.dp.toPx()) * currentProgress
            drawRoundRect(
                brush = leftBrush,
                topLeft = Offset(centerX - leftW, y),
                size = Size(leftW, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
            
            // Right Bar
            val rightW = (mirror.right / maxVal) * (width / 2 - 20.dp.toPx()) * currentProgress
            drawRoundRect(
                brush = rightBrush,
                topLeft = Offset(centerX, y),
                size = Size(rightW, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
        }
        
        // Center Line
        drawLine(
            color = colors.textPrimary.copy(alpha = 0.4f),
            start = Offset(centerX, 0f),
            end = Offset(centerX, height),
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Preview
@Composable
fun PreviewMirrorBarChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        MirrorBarChart(
            data = listOf(
                MirrorData(80f, 60f, "AGRI"),
                MirrorData(50f, 90f, "MFG"),
                MirrorData(100f, 110f, "SVC")
            ),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
