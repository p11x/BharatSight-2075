package com.bharatsight2075.ui.components

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.imageResource
import com.bharatsight2075.R

@Composable
fun IndiaMapImageOverlay(
    modifier: Modifier = Modifier,
    glowColor: Color = Color(0xFF00F5FF),
    animated: Boolean = true
) {
    // Load bitmap once
    val bitmap = ImageBitmap.imageResource(R.drawable.india_map_bg)

    // Glow pulse animation
    val glowPulse by if (animated) {
        rememberInfiniteTransition(label = "glow").animateFloat(
            0.55f, 1.0f,
            infiniteRepeatable(tween(2200, easing = EaseInOutSine), RepeatMode.Reverse),
            label = "gp"
        )
    } else {
        remember { mutableFloatStateOf(0.8f) }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint()

            // STEP 1: Draw base image darkened
            // Multiply blends only work correctly with pure black background.
            // Instead: draw image with SRC_IN after drawing a dark rect —
            // this masks image to its own alpha channel.
            // Since PNG background IS black (RGB 0,0,0), use ADD blend
            // to extract only the bright India shape pixels:
            paint.colorFilter = PorterDuffColorFilter(
                android.graphics.Color.argb(255, 0, 0, 0),
                PorterDuff.Mode.DARKEN
            )
            // Draw the bitmap scaled to canvas
            val bitmapAspect = bitmap.width.toFloat() / bitmap.height.toFloat()
            val canvasAspect  = size.width / size.height
            val (drawW, drawH) = if (bitmapAspect > canvasAspect) {
                (size.height * bitmapAspect) to size.height
            } else {
                size.width to (size.width / bitmapAspect)
            }
            val left = (size.width  - drawW) / 2f
            val top  = (size.height - drawH) / 2f
            val dst  = android.graphics.RectF(left, top, left + drawW, top + drawH)

            // LAYER 1: Darken the image itself (suppress near-black bg more)
            paint.colorFilter = PorterDuffColorFilter(
                android.graphics.Color.argb(200, 0, 8, 20),
                PorterDuff.Mode.MULTIPLY
            )
            canvas.nativeCanvas.drawBitmap(bitmap.asAndroidBitmap(), null, dst, paint)

            // LAYER 2: Cyan tint using SCREEN — bright pixels become cyan
            // Screen formula: result = 1-(1-src)*(1-dst)
            // Black src pixels → transparent. White src pixels → full cyan.
            val r = android.graphics.Color.red(glowColor.toArgb())
            val g = android.graphics.Color.green(glowColor.toArgb())
            val b = android.graphics.Color.blue(glowColor.toArgb())
            paint.colorFilter = PorterDuffColorFilter(
                android.graphics.Color.argb((200 * glowPulse).toInt(), r, g, b),
                PorterDuff.Mode.SCREEN
            )
            canvas.nativeCanvas.drawBitmap(bitmap.asAndroidBitmap(), null, dst, paint)

            // LAYER 3: ADD blend for extra brightness on bright pixels
            paint.colorFilter = PorterDuffColorFilter(
                android.graphics.Color.argb((80 * glowPulse).toInt(), r, g, b),
                PorterDuff.Mode.ADD
            )
            canvas.nativeCanvas.drawBitmap(bitmap.asAndroidBitmap(), null, dst, paint)
        }

        // LAYER 4: Radial glow bloom from India's center
        drawCircle(
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to glowColor.copy(0.12f * glowPulse),
                    0.5f to glowColor.copy(0.04f * glowPulse),
                    1.0f to Color.Transparent
                ),
                center = Offset(size.width * 0.43f, size.height * 0.47f),
                radius = size.width * 0.52f
            ),
            radius = size.width * 0.52f,
            center = Offset(size.width * 0.43f, size.height * 0.47f)
        )

        // BOTTOM FADE — seamless blend into screen below
        drawRect(
            Brush.verticalGradient(
                listOf(Color.Transparent, Color(0xFF050510)),
                startY = size.height * 0.72f,
                endY   = size.height
            )
        )

        // TOP FADE — subtle fade at top edge
        drawRect(
            Brush.verticalGradient(
                listOf(Color(0xFF050510), Color.Transparent),
                startY = 0f, endY = size.height * 0.06f
            )
        )

        // EDGE VIGNETTE — darkens corners to remove any box appearance
        drawRect(
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to Color.Transparent,
                    0.75f to Color.Transparent,
                    1.0f to Color(0xFF050510).copy(0.8f)
                ),
                center = Offset(size.width / 2f, size.height / 2f),
                radius = size.width * 0.75f
            )
        )
    }
}
