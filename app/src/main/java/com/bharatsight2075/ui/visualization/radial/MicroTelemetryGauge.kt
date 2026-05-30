package com.bharatsight2075.ui.visualization.radial

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.Dimensions
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MicroTelemetryGauge(
    modifier: Modifier = Modifier,
    name: String,
    value: Float,
    max: Float = 1.0f
) {
    val primaryColor = SciFiTheme.extendedColors.primary
    
    val animatedProgress by animateFloatAsState(
        targetValue = (value / max).coerceIn(0f, 1f),
        animationSpec = tween(1200, easing = LinearOutSlowInEasing),
        label = "GaugeFill"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "PulsingDot")
    val dotScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Scale"
    )

    Column(
        modifier = modifier
            .sizeIn(minWidth = Dimensions.GaugeMinSize, minHeight = Dimensions.GaugeMinSize)
            .padding(Dimensions.PaddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(64.dp)) {
                val strokeWidth = 6.dp.toPx()
                val sweepAngle = animatedProgress * 270f
                val startAngle = 135f

                // 1. Background track arc
                drawArc(
                    color = primaryColor.copy(alpha = 0.15f),
                    startAngle = startAngle,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // 2. Glow layer (bloom)
                drawArc(
                    color = primaryColor.copy(alpha = 0.3f),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
                )

                // 3. Main fill arc
                drawArc(
                    color = primaryColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // 4. Tick marks
                for (i in 0 until 8) {
                    val angleDeg = startAngle + (i * 270f / 7f)
                    val angleRad = Math.toRadians(angleDeg.toDouble())
                    val innerR = size.width / 2 + 4.dp.toPx()
                    val outerR = innerR + 3.dp.toPx()
                    
                    drawLine(
                        color = primaryColor.copy(alpha = 0.3f),
                        start = Offset(
                            (center.x + innerR * cos(angleRad)).toFloat(),
                            (center.y + innerR * sin(angleRad)).toFloat()
                        ),
                        end = Offset(
                            (center.x + outerR * cos(angleRad)).toFloat(),
                            (center.y + outerR * sin(angleRad)).toFloat()
                        ),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // 5. Pulsing dot at tip
                val endAngleRad = Math.toRadians((startAngle + sweepAngle).toDouble())
                val dotCenter = Offset(
                    (center.x + (size.width / 2) * cos(endAngleRad)).toFloat(),
                    (center.y + (size.width / 2) * sin(endAngleRad)).toFloat()
                )
                drawCircle(
                    color = primaryColor,
                    radius = 2.5.dp.toPx() * dotScale,
                    center = dotCenter
                )
            }

            // Center content
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${(value * 100).toInt()}%",
                    style = SciFiTheme.typography.BodyMono.copy(fontWeight = FontWeight.Bold),
                    color = primaryColor
                )
                Text(
                    text = name.uppercase(),
                    style = SciFiTheme.typography.ChartCaption,
                    color = primaryColor.copy(alpha = 0.5f)
                )
            }
        }
    }
}
