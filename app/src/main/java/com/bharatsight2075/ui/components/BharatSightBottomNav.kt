package com.bharatsight2075.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.screens.Routes
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun BharatSightBottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    hasNewUpdates: Boolean = false
) {
    val extendedColors = SciFiTheme.extendedColors
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .drawBehind {
                drawLine(
                    color = extendedColors.primary.copy(alpha = 0.2f),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            },
        color = Color(0xFF0A0A1A).copy(alpha = 0.95f)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = Icons.Outlined.Home,
                label = "Home",
                isSelected = currentRoute == Routes.HOME,
                onClick = { onNavigate(Routes.HOME) },
                colors = extendedColors
            )
            NavItem(
                icon = Icons.Outlined.Analytics,
                label = "Compare",
                isSelected = currentRoute == Routes.COMPARE,
                onClick = { onNavigate(Routes.COMPARE) },
                colors = extendedColors
            )
            NavItem(
                icon = Icons.Outlined.FlashOn,
                label = "Updates",
                isSelected = currentRoute == Routes.UPDATES,
                onClick = { onNavigate(Routes.UPDATES) },
                colors = extendedColors,
                hasBadge = hasNewUpdates
            )
            NavItem(
                icon = Icons.Outlined.AutoAwesome,
                label = "Ask",
                isSelected = currentRoute == Routes.ASK,
                onClick = { onNavigate(Routes.ASK) },
                colors = extendedColors
            )
            NavItem(
                icon = Icons.Outlined.Person,
                label = "Profile",
                isSelected = currentRoute == Routes.PROFILE,
                onClick = { onNavigate(Routes.PROFILE) },
                colors = extendedColors
            )
        }
    }
}

@Composable
private fun RowScope.NavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: SciFiTheme.SciFiColors,
    hasBadge: Boolean = false
) {
    val transition = updateTransition(isSelected, label = "NavTransition")
    val alpha by transition.animateFloat(label = "Alpha") { if (it) 1f else 0.4f }
    val scale by transition.animateFloat(label = "Scale") { if (it) 1.1f else 1f }
    
    val tint = if (isSelected) colors.primary else Color.White

    Column(
        modifier = Modifier
            .weight(1f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint.copy(alpha = alpha),
                modifier = Modifier.size(24.dp)
            )
            if (hasBadge) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .align(Alignment.TopEnd)
                        .background(colors.accent, CircleShape)
                )
            }
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .size(width = 24.dp, height = 2.dp)
                        .background(colors.primary, CircleShape)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 9.sp,
            color = tint.copy(alpha = alpha),
            fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
        )
    }
}
