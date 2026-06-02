package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun MicroTelemetryGauge(
    percentage: Float,  // Value from -100 to 100
    color: Color,
    modifier: Modifier = Modifier
) {
    // Default size: 36dp
     androidx.compose.foundation.Canvas(
         modifier = modifier
             .size(36.dp)
      ) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2
           val trackWidth = 3.dp
           
           // Draw dark background track
           drawCircle(
               color = androidx.compose.ui.graphics.Color(0xFF0A0A0A),  // Dark elevated black
               center = center,
               radius = radius - trackWidth.toPx()/2,
               style = androidx.compose.ui.graphics.drawscope.Stroke(width = trackWidth.toPx())
           )
           
           // Convert percentage to sweep angle (-100 to 100 -> 0 to 180 degrees for half gauge)
           // We'll show -100% as 0 degrees, 0% as 90 degrees, 100% as 180 degrees
           val sweepAngle = ((percentage + 100) / 200) * 180
           
           // Draw the value arc
           drawArc(
               color = color,
               startAngle = 90f,  // Start at top
               sweepAngle = sweepAngle,
               useCenter = false,
               topLeft = Offset(center.x - radius + trackWidth.toPx()/2, center.y - radius + trackWidth.toPx()/2),
               size = androidx.compose.ui.geometry.Size(
                   width = (radius - trackWidth.toPx()/2) * 2,
                   height = (radius - trackWidth.toPx()/2) * 2
               ),
               style = androidx.compose.ui.graphics.drawscope.Stroke(width = trackWidth.toPx())
           )
      }
}