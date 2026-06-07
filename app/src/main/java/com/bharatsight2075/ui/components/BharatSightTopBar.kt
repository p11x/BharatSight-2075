package com.bharatsight2075.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

sealed class TopBarMode {
    data class Home(
        val userName: String,
        val onMenuClick: () -> Unit,
        val onSettingsClick: () -> Unit,
        val onNotificationsClick: () -> Unit,
        val liveStatusText: String = "SYSTEMS ONLINE",
        val notificationCount: Int = 0
    ) : TopBarMode()

    data class Section(
        val title: String,
        val badge: String? = null,
        val badgeColor: Color? = null,
        val onBackClick: () -> Unit,
        val actions: List<TopBarAction> = emptyList()
    ) : TopBarMode()
}

data class TopBarAction(
    val icon: ImageVector,
    val contentDesc: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BharatSightTopBar(
    mode: TopBarMode,
    modifier: Modifier = Modifier
) {
    val extendedColors = SciFiTheme.extendedColors
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = Color.Transparent
    ) {
        when (mode) {
            is TopBarMode.Home -> HomeTopBarContent(mode, extendedColors)
            is TopBarMode.Section -> SectionTopBarContent(mode, extendedColors)
        }
    }
}

@Composable
private fun HomeTopBarContent(mode: TopBarMode.Home, colors: SciFiTheme.SciFiColors) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Menu Button
        IconButton(
            onClick = mode.onMenuClick,
            modifier = Modifier
                .size(40.dp)
                .background(colors.primary.copy(alpha = 0.05f), CircleShape)
                .border(1.dp, colors.primary.copy(alpha = 0.2f), CircleShape)
        ) {
            Icon(Icons.Outlined.Menu, contentDescription = "Menu", tint = colors.primary, modifier = Modifier.size(20.dp))
        }

        // Center Identity
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Hi, ${mode.userName}",
                style = SciFiTheme.typography.BodyMono,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(colors.positive, CircleShape)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = mode.liveStatusText,
                    fontSize = 10.sp,
                    color = colors.positive.copy(alpha = 0.7f),
                    letterSpacing = 1.sp
                )
            }
        }

        // Right Actions
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BadgedBox(
                badge = {
                    if (mode.notificationCount > 0) {
                        Badge(containerColor = colors.accent) {
                            Text(mode.notificationCount.toString(), color = Color.Black, fontSize = 8.sp)
                        }
                    }
                }
            ) {
                IconButton(
                    onClick = mode.onNotificationsClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Color.White)
                }
            }

            IconButton(
                onClick = mode.onSettingsClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }
    }
}

@Composable
private fun SectionTopBarContent(mode: TopBarMode.Section, colors: SciFiTheme.SciFiColors) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = mode.onBackClick,
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.05f), CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back", tint = colors.primary)
        }
        
        Spacer(Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = mode.title.uppercase(),
                style = SciFiTheme.typography.SectionHead,
                color = Color.White,
                fontSize = 16.sp
            )
            if (mode.badge != null) {
                Surface(
                    modifier = Modifier.padding(top = 2.dp),
                    color = (mode.badgeColor ?: colors.primary).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(0.5.dp, (mode.badgeColor ?: colors.primary).copy(alpha = 0.5f))
                ) {
                    Text(
                        text = mode.badge,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 8.sp,
                        color = mode.badgeColor ?: colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            mode.actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(action.icon, contentDescription = action.contentDesc, tint = Color.White)
                }
            }
        }
    }
}
