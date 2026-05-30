package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.Stroke
import com.bharatsight2075.ui.theme.RetroDarkColors
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.layout.Arrangement

@Preview(showBackground = true, widthDp = 200, heightDp = 100)
@Composable
fun CommandCenterNavItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "CommandCenterNavItem Test",
            color = RetroDarkColors.TextPrimary
        )
        
        CommandCenterNavItem(
            label = "OVERVIEW",
            isSelected = true,
            color = RetroDarkColors.NeonYellow,
            trailingContent = {
                // Empty trailing content for this test
            },
            onClick = {}
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        CommandCenterNavItem(
            label = "ANALYSIS",
            isSelected = false,
            color = RetroDarkColors.NeonCyan,
            trailingContent = {
                Row(
                    modifier = Modifier
                        .height(20.dp)
                        .padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Mock micro-components for preview
                    Canvas(
                        modifier = Modifier
                            .width(40.dp)
                            .height(16.dp)
                    ) {
                        // Simple line for preview
                        val path = androidx.compose.ui.graphics.Path().apply {
                            moveTo(0f, 8f)
                            lineTo(40f, 8f)
                        }
                          drawPath(path, color = RetroDarkColors.NeonCyan, style = Stroke(width = 1.5F))
                    }
                    
                    Canvas(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    ) {
                        // Simple arc for preview
                        val sweepAngle = 90f
                        drawArc(
                            color = RetroDarkColors.NeonGreen,
                            startAngle = 90f,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            topLeft = Offset(0f, 0f),
                             size = androidx.compose.ui.geometry.Size(width = 24f, height = 24f),
                             style = Stroke(width = 3f)
                        )
                    }
                }
            },
            onClick = {}
        )
    }
}