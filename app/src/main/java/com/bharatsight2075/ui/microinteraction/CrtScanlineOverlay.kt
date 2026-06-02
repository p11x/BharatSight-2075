package com.bharatsight2075.ui.microinteraction

import android.content.Context
import android.os.PowerManager
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Sci-fi retro CRT effect that renders subtle scanlines.
 * Automatically disables in low-power mode to save GPU cycles.
 */
fun Modifier.crtScanlineOverlay(): Modifier = composed {
    val context = LocalContext.current
    val powerManager = remember { context.getSystemService(Context.POWER_SERVICE) as PowerManager }
    val isPowerSaveMode = powerManager.isPowerSaveMode

    if (isPowerSaveMode) return@composed this

    this.drawWithContent {
        drawContent()

        val scanlineColor = Color.Black.copy(alpha = 0.03f)
        val stride = 4
        
        for (y in 0 until size.height.toInt() step stride) {
            drawLine(
                color = scanlineColor,
                start = Offset(0f, y.toFloat()),
                end = Offset(size.width, y.toFloat()),
                strokeWidth = 1f
            )
        }
    }
}
