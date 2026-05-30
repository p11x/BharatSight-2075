package com.bharatsight2075.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object GradPalette {
    val TEAL_PURPLE = Brush.linearGradient(listOf(Color(0xFF00F5FF), Color(0xFF7B2FBE)))
    val ORANGE_PINK = Brush.linearGradient(listOf(Color(0xFFFF6B35), Color(0xFFE91E8C)))
    val GREEN_TEAL = Brush.linearGradient(listOf(Color(0xFF00E676), Color(0xFF00B0FF)))
    val YELLOW_ORANGE = Brush.linearGradient(listOf(Color(0xFFFFD600), Color(0xFFFF6B35)))
    val PURPLE_BLUE = Brush.linearGradient(listOf(Color(0xFF7C4DFF), Color(0xFF2979FF)))
    val PINK_PURPLE = Brush.linearGradient(listOf(Color(0xFFF06292), Color(0xFF7C4DFF)))
    val GOLD_WHITE = Brush.linearGradient(listOf(Color(0xFFFFD700), Color(0xFFFFFFFF)))
    val CYAN_WHITE = Brush.linearGradient(listOf(Color(0xFF00F5FF), Color(0xFFFFFFFF).copy(alpha = 0.8f)))
    
    /**
     * Helper to get a vertical gradient for chart area fills.
     * Fades from the provided color at 60% alpha to transparent.
     */
    fun areaFill(color: Color): Brush = Brush.verticalGradient(
        listOf(color.copy(alpha = 0.6f), Color.Transparent)
    )
}
