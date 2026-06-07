package com.bharatsight2075.ui.maps

import android.graphics.BlurMaskFilter
import android.graphics.Paint as AndroidPaint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharatsight2075.viewmodel.IndiaMapViewModel

@Composable
fun NeonIndiaMap(
    viewModel: IndiaMapViewModel,
    modifier: Modifier = Modifier,
    onStateClick: (String) -> Unit = {},
    highlightedState: String? = null,
    showCities: Boolean = true,
    borderColor: Color = Color(0xFF00FFFF),
    fillColor: Color = Color(0xFF010D18)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "NeonMapTransitions")
    
    val cityPulse by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "CityPulse"
    )

    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowPulse"
    )

    val cache by viewModel.pathCache.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(fillColor)
            .pointerInput(cache) {
                detectTapGestures { offset ->
                    cache?.statePaths?.forEach { (state, paths) ->
                        paths.forEach { androidPath ->
                            val region = android.graphics.Region()
                            val rectF = android.graphics.RectF()
                            androidPath.computeBounds(rectF, true)
                            region.setPath(androidPath, android.graphics.Region(
                                rectF.left.toInt(), rectF.top.toInt(),
                                rectF.right.toInt(), rectF.bottom.toInt()
                            ))
                            if (region.contains(offset.x.toInt(), offset.y.toInt())) {
                                onStateClick(IndiaMapGeoParser.mapStateNameToId(state.name))
                                return@detectTapGestures
                            }
                        }
                    }
                }
            }
            .onSizeChanged { size ->
                viewModel.buildPathCache(size.width.toFloat(), size.height.toFloat())
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val c = cache ?: run {
                // Show skeleton while paths build
                drawRect(fillColor.copy(0.3f))
                drawCircle(borderColor.copy(0.1f), size.minDimension * 0.4f, center)
                return@Canvas
            }

            // PASS 1: Fills
            val stateFillColor = Color(0xFF0A1628).copy(alpha = 180f / 255f)
            val highlightedFillColor = borderColor.copy(alpha = 40f / 255f)

            c.statePaths.forEach { (state, paths) ->
                val id = IndiaMapGeoParser.mapStateNameToId(state.name)
                val isHighlighted = id == highlightedState
                paths.forEach { path ->
                    drawPath(
                        path = path.asComposePath(),
                        color = if (isHighlighted) highlightedFillColor else stateFillColor
                    )
                }
            }

            // PASSES 2-5: Glow strokes
            val passes = listOf(
                Triple(14.dp.toPx(), 0.06f * glowPulse, 15.dp.toPx()),
                Triple(6.dp.toPx(), 0.18f * glowPulse, 6.dp.toPx()),
                Triple(2.5.dp.toPx(), 0.40f, 2.dp.toPx()),
                Triple(1.2.dp.toPx(), 0.90f, 0f)
            )

            passes.forEach { (passWidth, passAlpha, blurRadius) ->
                val paint = AndroidPaint().apply {
                    isAntiAlias = true
                    style = AndroidPaint.Style.STROKE
                    strokeWidth = passWidth
                    color = borderColor.copy(alpha = passAlpha).toArgb()
                    strokeCap = AndroidPaint.Cap.ROUND
                    strokeJoin = AndroidPaint.Join.ROUND
                    if (blurRadius > 0f) {
                        maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
                    }
                }

                drawIntoCanvas { canvas ->
                    val nativeCanvas = canvas.nativeCanvas
                    
                    // State borders
                    c.statePaths.forEach { (_, paths) ->
                        paths.forEach { path ->
                            nativeCanvas.drawPath(path, paint)
                        }
                    }
                }
            }

            // City Markers
            if (showCities) {
                val cities = listOf(
                    "Delhi" to (28.6f to 77.2f),
                    "Mumbai" to (19.1f to 72.9f),
                    "Bangalore" to (12.9f to 77.6f),
                    "Chennai" to (13.1f to 80.3f),
                    "Kolkata" to (22.6f to 88.4f),
                    "Hyderabad" to (17.4f to 78.5f),
                    "Pune" to (18.5f to 73.9f),
                    "Ahmedabad" to (23.0f to 72.6f),
                    "Lucknow" to (26.8f to 80.9f),
                    "Patna" to (25.6f to 85.1f),
                    "Bhubaneswar" to (20.3f to 85.8f),
                    "Guwahati" to (26.2f to 91.7f)
                )

                cities.forEach { (_, coords) ->
                    val cx = IndiaMapGeoParser.lngToX(coords.second, size.width)
                    val cy = IndiaMapGeoParser.latToY(coords.first, size.height)
                    val markerCenter = Offset(cx, cy)

                    // Outer ring: cyan 0xFF00FFFF, radius=8dp, alpha animated 255→80
                    drawCircle(
                        color = Color(0xFF00FFFF).copy(alpha = 1f - (cityPulse * 0.68f)), // 255 to ~80
                        radius = 8.dp.toPx(),
                        center = markerCenter,
                        style = Stroke(width = 1.5.dp.toPx())
                    )

                    // Inner dot: orange 0xFFFF6600, radius=3dp, solid
                    drawCircle(
                        color = Color(0xFFFF6600),
                        radius = 3.dp.toPx(),
                        center = markerCenter
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NeonIndiaMapPreview() {
    // Note: In a real preview you'd need a mock ViewModel or separate Stateless version
    // NeonIndiaMap(
    //    viewModel = hiltViewModel(),
    //    modifier = Modifier.fillMaxWidth().height(600.dp)
    // )
}
