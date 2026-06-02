package com.bharatsight2075.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun ConfidenceIntervalChart(
    modifier: Modifier = Modifier,
    prediction: FloatArray,
    lowerBound: FloatArray,
    upperBound: FloatArray,
    color: Color = RetroDarkColors.NeonCyan
) {
    // Ensure bounds are sorted (P10 <= prediction <= P90)
    val sortedLower = FloatArray(prediction.size) { i ->
        minOf(lowerBound[i], upperBound[i])
    }
    val sortedUpper = FloatArray(prediction.size) { i ->
        maxOf(lowerBound[i], upperBound[i])
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val stepX = width / (prediction.size - 1)
        val maxValue = (prediction + sortedUpper).maxOrNull() ?: 1f
        val scaleY = height / maxValue

        // Draw Confidence Band
        val bandPath = Path().apply {
            moveTo(0f, height - sortedLower[0] * scaleY)
            for (i in 1 until sortedLower.size) {
                lineTo(i * stepX, height - sortedLower[i] * scaleY)
            }
            for (i in sortedUpper.size - 1 downTo 0) {
                lineTo(i * stepX, height - sortedUpper[i] * scaleY)
            }
            close()
        }
        drawPath(
            path = bandPath,
            color = color.copy(alpha = 0.2f),
            style = Fill
        )

        // Draw Prediction Line
        val linePath = Path().apply {
            moveTo(0f, height - prediction[0] * scaleY)
            for (i in 1 until prediction.size) {
                lineTo(i * stepX, height - prediction[i] * scaleY)
            }
        }
        drawPath(
            path = linePath,
            color = color,
            style = Stroke(width = 3f)
        )
    }
}
