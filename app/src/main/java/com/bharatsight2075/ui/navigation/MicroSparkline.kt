package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun MicroSparkline(
    data: List<Float>,
    color: Color,
    modifier: Modifier = Modifier
) {
    // Default size: 40dp width, 16dp height
     androidx.compose.foundation.Canvas(
         modifier = modifier
             .width(40.dp)
             .height(16.dp)
     ) {
         if (data.isEmpty() || data.size < 2) return@Canvas
 
         val path = androidx.compose.ui.graphics.Path().apply {
             // Normalize data points to 0-1 range for consistent scaling
             val normalizedData = data.map { 
                 (it - data.minOrNull()!!) / (data.maxOrNull()!! - data.minOrNull()!! + 0.0001f) 
             }
             
               normalizedData.forEachIndexed { index, value ->
                    val x = index * (size.width / (normalizedData.size - 1))
                    val y = size.height * (1 - value) // Invert Y for typical graph orientation
                    
                    if (index == 0) {
                        moveTo(x, y)
                    } else {
                        lineTo(x, y)
                    }
                }
         }
        
         drawPath(
             path = path,
             color = color,
             style = Stroke(width = 1.5f)
         )
    }
}