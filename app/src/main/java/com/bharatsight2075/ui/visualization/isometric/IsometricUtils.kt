package com.bharatsight2075.ui.visualization.isometric

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.math.cos
import kotlin.math.sin

object IsometricUtils {
    // 30 degrees in radians
    private const val ANGLE = 0.52359877559f 
    private val COS_30 = cos(ANGLE)
    private val SIN_30 = sin(ANGLE)

    /**
     * Projects a 3D coordinate (x, y, z) into 2D screen space.
     * x: horizontal axis
     * y: vertical axis (height, where negative is UP in screen space)
     * z: depth axis
     */
    fun projectToIsometric(x: Float, y: Float, z: Float): Offset {
        val screenX = (x - z) * COS_30
        val screenY = y + (x + z) * SIN_30
        return Offset(screenX, screenY)
    }

    /**
     * Draws a 3D box using isometric projection with simulated lighting.
     */
    fun DrawScope.drawIsometricCube(
        x: Float, 
        y: Float, 
        z: Float, 
        width: Float, 
        height: Float, 
        depth: Float, 
        color: Color
    ) {
        // Calculate the 8 vertices of the cube
        // Bottom vertices (y = y)
        val p1 = projectToIsometric(x, y, z)                   // Front-bottom
        val p2 = projectToIsometric(x + width, y, z)           // Right-bottom
        val p3 = projectToIsometric(x + width, y, z + depth)   // Back-bottom
        val p4 = projectToIsometric(x, y, z + depth)           // Left-bottom

        // Top vertices (y = y - height)
        val tY = y - height
        val v1 = projectToIsometric(x, tY, z)                  // Front-top
        val v2 = projectToIsometric(x + width, tY, z)          // Right-top
        val v3 = projectToIsometric(x + width, tY, z + depth)  // Back-top
        val v4 = projectToIsometric(x, tY, z + depth)          // Left-top

        // 1. TOP FACE (Lightest shade)
        val topPath = Path().apply {
            moveTo(v1.x, v1.y)
            lineTo(v2.x, v2.y)
            lineTo(v3.x, v3.y)
            lineTo(v4.x, v4.y)
            close()
        }
        drawPath(topPath, color)

        // 2. LEFT FACE (Medium shade - 20% darker)
        val leftPath = Path().apply {
            moveTo(v1.x, v1.y)
            lineTo(p1.x, p1.y)
            lineTo(p4.x, p4.y)
            lineTo(v4.x, v4.y)
            close()
        }
        drawPath(leftPath, color.darken(0.2f))

        // 3. RIGHT FACE (Darkest shade - 40% darker)
        val rightPath = Path().apply {
            moveTo(v1.x, v1.y)
            lineTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            lineTo(v2.x, v2.y)
            close()
        }
        drawPath(rightPath, color.darken(0.4f))
    }

    private fun Color.darken(factor: Float): Color {
        return Color(
            red = (red * (1f - factor)).coerceIn(0f, 1f),
            green = (green * (1f - factor)).coerceIn(0f, 1f),
            blue = (blue * (1f - factor)).coerceIn(0f, 1f),
            alpha = alpha
        )
    }
}
