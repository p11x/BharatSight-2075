package com.bharatsight2075.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bharatsight2075.ui.screens.Routes
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlinx.coroutines.delay

@Composable
fun DashCard(
    chartId: String,
    navController: NavController,
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
    badge: String? = null,
    showLiveDot: Boolean = false,
    cardIndex: Int = 0,
    content: @Composable BoxScope.() -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    val accent = extendedColors.accent
    
    var visible by remember { mutableStateOf(false) }
    val staggerOffset by animateDpAsState(
        targetValue = if (visible) 0.dp else 24.dp,
        animationSpec = spring(stiffness = 150f, dampingRatio = 0.7f),
        label = "staggerOffset"
    )
    val staggerAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400),
        label = "staggerAlpha"
    )
    
    LaunchedEffect(Unit) {
        delay(cardIndex * 60L)
        visible = true
    }
    
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.97f else 1f, spring(stiffness = 600f), label = "DashCardScale")

    Box(
        modifier = modifier
            .offset(y = staggerOffset)
            .alpha(staggerAlpha)
            .graphicsLayer { 
                scaleX = scale
                scaleY = scale 
            }
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = accent.copy(alpha = 0.15f),
                spotColor = accent.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(accent.copy(alpha = 0.4f), accent.copy(alpha = 0.1f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color(0xFF0E0E1A))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(Routes.CHART_DETAIL.replace("{chartId}", chartId))
            }
            .drawBehind {
                drawRect(
                    brush = Brush.linearGradient(
                        listOf(accent.copy(alpha = 0.05f), Color.Transparent)
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
                
                // Expand hint in header
                Icon(
                    imageVector = Icons.Outlined.OpenInFull,
                    contentDescription = "Expand",
                    tint = accent.copy(alpha = 0.35f),
                    modifier = Modifier.size(14.dp)
                )
            }
            
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    color = SciFiTheme.colors.onSurface.copy(0.45f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp, start = 26.dp)
                )
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
