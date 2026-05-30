package com.bharatsight2075.ui.visualization

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object GradientFills {
    private const val MIN_RADIUS = 0.01f

    // Area chart fill (top=color, bottom=transparent)
    fun areaFill(color: Color, height: Float) =
        Brush.verticalGradient(
            listOf(color.copy(alpha = 0.65f), color.copy(alpha = 0.0f)),
            0f, height.coerceAtLeast(1f)
        )

    // Bar fill (bottom=dark, top=bright)
    fun barFill(topColor: Color, bottomColor: Color, barTop: Float, barBottom: Float) =
        Brush.verticalGradient(
            listOf(topColor, bottomColor),
            barTop, barBottom.coerceAtLeast(barTop + 1f)
        )

    // Donut/arc stroke fill
    fun arcStroke(startColor: Color, endColor: Color) =
        Brush.sweepGradient(listOf(startColor, endColor, startColor))

    // Bubble radial fill
    fun bubbleFill(color: Color, center: Offset, radius: Float) =
        Brush.radialGradient(
            listOf(Color.White.copy(alpha = 0.6f), color, color.copy(alpha = 0.3f)),
            center = center, 
            radius = radius.coerceAtLeast(MIN_RADIUS)
        )

    // Glow overlay (drawn BEHIND any shape)
    fun glowHalo(color: Color, center: Offset, radius: Float) =
        Brush.radialGradient(
            listOf(color.copy(alpha = 0.3f), color.copy(alpha = 0.1f), Color.Transparent),
            center = center, 
            radius = (radius * 2f).coerceAtLeast(MIN_RADIUS)
        )
}
