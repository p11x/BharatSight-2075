package com.bharatsight2075.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.OpenInFull
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bharatsight2075.ui.screens.Routes
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun DashCard(
    chartId: String,
    navController: NavController,
    title: String,
    modifier: Modifier = Modifier,
    badge: String? = null,
    showLiveDot: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    val primary = extendedColors.primary
    val accent = extendedColors.accent
    val isCyberpunk = SciFiTheme.current == SciFiTheme.Theme.Cyberpunk
    
    val backgroundColor = if (isCyberpunk) Color(0xFF0E0E1A) else Color(0xFF060E1C)
    
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.97f else 1f, spring(stiffness = 600f), label = "DashCardScale")

    Box(
        modifier = modifier
            .graphicsLayer { 
                scaleX = scale
                scaleY = scale 
            }
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(primary.copy(alpha = 0.4f), accent.copy(alpha = 0.2f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(Routes.CHART_DETAIL.replace("{chartId}", chartId))
            }
            .drawBehind {
                // Subtle top-edge glow
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(primary.copy(alpha = 0.06f), Color.Transparent),
                        endY = 40f
                    )
                )
            }
            .padding(16.dp)
    ) {
        Column {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (showLiveDot) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Color(0xFF00E676), androidx.compose.foundation.shape.CircleShape)
                        )
                    }
                    Text(
                        text = title.uppercase(),
                        style = SciFiTheme.typography.SectionHead,
                        color = extendedColors.textPrimary.copy(alpha = 0.75f)
                    )
                    if (badge != null) {
                        BadgeChip(badge)
                    }
                }
                
                // Tap hint icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.OpenInFull,
                        contentDescription = "Expand",
                        tint = primary.copy(alpha = 0.4f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = "EXPAND",
                        fontSize = 8.sp,
                        color = primary.copy(alpha = 0.4f),
                        style = SciFiTheme.typography.BodyMono
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

@Composable
fun BadgeChip(label: String) {
    Surface(
        color = SciFiTheme.extendedColors.primary.copy(alpha = 0.15f),
        shape = RoundedCornerShape(4.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, SciFiTheme.extendedColors.primary.copy(alpha = 0.4f))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            fontSize = 8.sp,
            color = SciFiTheme.extendedColors.primary,
            style = SciFiTheme.typography.BodyMono
        )
    }
}
