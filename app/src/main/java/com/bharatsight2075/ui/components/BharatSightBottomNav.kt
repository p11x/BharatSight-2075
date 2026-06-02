package com.bharatsight2075.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.screens.Routes
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BharatSightBottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    hasNewUpdates: Boolean = false
) {
    val colors = SciFiTheme.extendedColors
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(colors.surface)
            .drawBehind {
                drawLine(
                    color = colors.primary.copy(alpha = 0.2f),
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
    ) {
        // Top Gradient Fade
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(colors.primary.copy(alpha = 0.04f), Color.Transparent)
                    )
                )
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = Icons.Outlined.Home,
                activeIcon = Icons.Filled.Home,
                label = "Home",
                isSelected = currentRoute == Routes.HOME,
                onClick = { onNavigate(Routes.HOME) }
            )
            NavItem(
                icon = Icons.Outlined.BarChart,
                activeIcon = Icons.Filled.BarChart,
                label = "Compare",
                isSelected = currentRoute == Routes.COMPARE,
                onClick = { onNavigate(Routes.COMPARE) }
            )
            NavItem(
                icon = Icons.Outlined.Bolt,
                activeIcon = Icons.Filled.Bolt,
                label = "Updates",
                isSelected = currentRoute == Routes.UPDATES,
                onClick = { onNavigate(Routes.UPDATES) },
                showBadge = hasNewUpdates
            )
            NavItem(
                icon = Icons.Outlined.AutoAwesome,
                activeIcon = Icons.Filled.AutoAwesome,
                label = "Ask",
                isSelected = currentRoute == Routes.ASK,
                onClick = { onNavigate(Routes.ASK) },
                isSpecial = true
            )
            NavItem(
                icon = Icons.Outlined.Person,
                activeIcon = Icons.Filled.Person,
                label = "Profile",
                isSelected = currentRoute == Routes.PROFILE,
                onClick = { onNavigate(Routes.PROFILE) }
            )
        }
    }
}

@Composable
private fun RowScope.NavItem(
    icon: ImageVector,
    activeIcon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    showBadge: Boolean = false,
    isSpecial: Boolean = false
) {
    val colors = SciFiTheme.extendedColors
    val primary = colors.primary
    val scope = rememberCoroutineScope()
    
    var itemScale by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = itemScale,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "Scale"
    )

    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 0.dp,
        animationSpec = spring(stiffness = 500f),
        label = "IndicatorWidth"
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                scope.launch {
                    itemScale = 0.9f
                    delay(50)
                    itemScale = 1f
                }
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Indicator
        Box(
            modifier = Modifier
                .width(indicatorWidth)
                .height(3.dp)
                .background(primary, RoundedCornerShape(1.5.dp))
        )
        
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .size(40.dp)
                .scale(animatedScale)
                .drawBehind {
                    if (isSelected) {
                        drawCircle(
                            brush = Brush.radialGradient(
                                listOf(primary.copy(alpha = 0.2f), Color.Transparent)
                            ),
                            radius = if (isSpecial) 28.dp.toPx() else size.width / 2
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) activeIcon else icon,
                contentDescription = label,
                tint = if (isSelected) primary else colors.textPrimary.copy(alpha = 0.45f),
                modifier = Modifier.size(22.dp)
            )
            
            if (showBadge) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 4.dp, end = 4.dp)
                        .size(6.dp)
                        .background(Color.Red, CircleShape)
                )
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = if (isSelected) primary else colors.textPrimary.copy(alpha = 0.4f),
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun PreviewBottomNavHome() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        BharatSightBottomNav(currentRoute = Routes.HOME, onNavigate = {})
    }
}

@Preview
@Composable
fun PreviewBottomNavAsk() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Hologram) {
        BharatSightBottomNav(currentRoute = Routes.ASK, onNavigate = {})
    }
}
