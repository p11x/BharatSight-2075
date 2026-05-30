package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.RetroDarkColors
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun CommandCenterNavItem(
    label: String,
    isSelected: Boolean = false,
    color: Color = RetroDarkColors.TextPrimary,
    trailingContent: @Composable () -> Unit = {},
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        // Selection indicator - glowing vertical bar on far left when selected
        if (isSelected) {
            androidx.compose.foundation.Canvas(
                modifier = Modifier.width(3.dp)
            ) {
                // Draw a glowing bar (we'll simulate glow with a slightly larger bright bar)
                drawRect(
                    color = RetroDarkColors.NeonOrange,
                    topLeft = androidx.compose.ui.geometry.Offset(0f, 0f),
                    size = androidx.compose.ui.geometry.Size(3.dp.toPx(), 48.dp.toPx()),
                    style = androidx.compose.ui.graphics.drawscope.Fill
                )
            }
            // Add some spacing after the selection indicator
            Spacer(modifier = Modifier.width(4.dp))
        }
        
        // Text label
        androidx.compose.material3.Text(
            text = label.uppercase(),
            color = if (isSelected) color else color.copy(alpha = 0.6f),
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        
        // Trailing content slot for micro-visualizations
        trailingContent()
    }
}