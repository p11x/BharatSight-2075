package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.GradientFills

/**
 * C25. MiniSparklineCard
 */
@Composable
fun MiniSparklineCard(
    label: String,
    value: String,
    data: List<Float> = emptyList(),
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.LINE).filterIsInstance<Float>()
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "SparkAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = SciFiTheme.typography.ChartCaption, color = colors.textSecondary)
            Text(value, style = SciFiTheme.typography.BodyMono.copy(fontWeight = FontWeight.Bold), color = colors.textPrimary)
        }
        
        Canvas(modifier = Modifier.size(80.dp, 40.dp)) {
            val count = safeData.size
            if (count < 2) return@Canvas
            
            val spacing = size.width / (count - 1)
            val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
            
            val points = safeData.mapIndexed { i, v ->
                Offset(i * spacing, size.height - (v / maxVal * size.height * currentProgress))
            }
            
            val path = Path().apply {
                moveTo(points[0].x, points[0].y)
                points.drop(1).forEach { lineTo(it.x, it.y) }
            }
            
            drawPath(path, colors.primary, style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
        }
    }
}
