package com.bharatsight2075.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * Standard container for all data visualizations in the app.
 * Follows the data-dense, futuristic sci-fi aesthetic.
 */
@Composable
fun DashCard(
    title: String,
    modifier: Modifier = Modifier,
    showLiveDot: Boolean = false,
    onMenuClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    val primary = extendedColors.primary
    val accent = extendedColors.accent
    val isCyberpunk = SciFiTheme.current == SciFiTheme.Theme.Cyberpunk
    
    val backgroundColor = if (isCyberpunk) Color(0xFF0E0E1A) else Color(0xFF060E1C)
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = primary.copy(alpha = 0.2f),
                spotColor = primary.copy(alpha = 0.15f)
            )
            .drawBehind {
                // Subtle top-edge glow
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(primary.copy(alpha = 0.06f), Color.Transparent),
                        endY = 40f
                    )
                )
            },
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            brush = Brush.linearGradient(
                listOf(primary.copy(alpha = 0.5f), accent.copy(alpha = 0.3f), primary.copy(alpha = 0.1f))
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showLiveDot) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Color(0xFF00E676), androidx.compose.foundation.shape.CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = title.uppercase(),
                        style = SciFiTheme.typography.SectionHead,
                        color = extendedColors.textPrimary.copy(alpha = 0.7f)
                    )
                }
                
                IconButton(onClick = onMenuClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "Menu",
                        tint = extendedColors.textSecondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Content Area
            Box(
                modifier = Modifier.fillMaxWidth(),
                content = content
            )
        }
    }
}
