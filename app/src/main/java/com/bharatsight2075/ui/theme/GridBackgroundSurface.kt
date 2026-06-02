package com.bharatsight2075.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp

@Composable
fun GridBackgroundSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    val currentTheme = SciFiTheme.current
    
    val gridAlpha = if (currentTheme == SciFiTheme.Theme.Cyberpunk) 0.06f else 0.04f
    val strokeWidth = 0.5.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(extendedColors.background)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    
                    val radialMask = Brush.radialGradient(
                        0.0f to Color.White,
                        0.7f to Color.White.copy(alpha = 0.3f),
                        1.0f to Color.Transparent,
                        center = center,
                        radius = size.maxDimension / 1.5f
                    )
                    
                    drawIntoCanvas { canvas ->
                        canvas.saveLayer(size.toRect(), Paint())
                        
                        val step = 40.dp.toPx()
                        val rows = (size.height / step).toInt()
                        val cols = (size.width / step).toInt()
                        
                        for (i in 0..rows) {
                            val y = i * step
                            drawLine(
                                color = extendedColors.gridLine.copy(alpha = gridAlpha * 1.2f),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth.toPx()
                            )
                        }
                        
                        for (i in 0..cols) {
                            val x = i * step
                            drawLine(
                                color = extendedColors.gridLine.copy(alpha = gridAlpha * 1.2f),
                                start = Offset(x, 0f),
                                end = Offset(x, size.height),
                                strokeWidth = strokeWidth.toPx()
                            )
                        }

                        drawRect(
                            brush = radialMask,
                            blendMode = BlendMode.DstIn
                        )
                        
                        canvas.restore()
                    }
                }
        ) {
            drawRect(
                brush = Brush.radialGradient(
                    0.0f to Color.Transparent,
                    1.0f to extendedColors.background.copy(alpha = 0.4f),
                    center = center,
                    radius = size.maxDimension / 1.2f
                )
            )
        }
        content()
    }
}

private fun androidx.compose.ui.geometry.Size.toRect() = androidx.compose.ui.geometry.Rect(0f, 0f, width, height)
