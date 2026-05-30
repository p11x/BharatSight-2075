package com.bharatsight2075.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * High-fidelity TopBar that switches between Home and Section modes.
 */
sealed class TopBarMode {
    data class Home(
        val userName: String,
        val onMenuClick: () -> Unit,
        val onSettingsClick: () -> Unit,
        val onNotificationsClick: () -> Unit = {},
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

data class TopBarAction(val icon: ImageVector, val contentDesc: String, val onClick: () -> Unit)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BharatSightTopBar(
    mode: TopBarMode,
    modifier: Modifier = Modifier
) {
    val extendedColors = SciFiTheme.extendedColors
    val primary = extendedColors.primary
    val isCyberpunk = SciFiTheme.current == SciFiTheme.Theme.Cyberpunk
    
    val backgroundColor = if (isCyberpunk) Color(0xFF080810) else Color(0xFF040C18)

    TopAppBar(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = primary.copy(alpha = 0.2f),
                    start = androidx.compose.ui.geometry.Offset(0f, size.height),
                    end = androidx.compose.ui.geometry.Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                when (mode) {
                    is TopBarMode.Home -> HomeTopBarContent(mode)
                    is TopBarMode.Section -> SectionTopBarContent(mode)
                }
            }
        },
        navigationIcon = {
            if (mode is TopBarMode.Home) {
                IconButton(onClick = mode.onMenuClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = primary)
                }
            } else if (mode is TopBarMode.Section) {
                IconButton(onClick = mode.onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = primary)
                }
            }
        },
        actions = {
            if (mode is TopBarMode.Home) {
                IconButton(onClick = mode.onNotificationsClick) {
                    BadgedBox(
                        badge = {
                            if (mode.notificationCount > 0) {
                                Badge(containerColor = extendedColors.accent) {
                                    Text(mode.notificationCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = primary)
                    }
                }
                IconButton(onClick = mode.onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = primary)
                }
            } else if (mode is TopBarMode.Section) {
                mode.actions.forEach { action ->
                    IconButton(onClick = action.onClick) {
                        Icon(action.icon, contentDescription = action.contentDesc, tint = primary)
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = Color.White
        )
    )
}

@Composable
private fun HomeTopBarContent(mode: TopBarMode.Home) {
    val extendedColors = SciFiTheme.extendedColors
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Hi, ${mode.userName}",
                style = SciFiTheme.typography.BodyMono.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("👋", fontSize = 14.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(Color(0xFF00E676), CircleShape)
                    .drawBehind {
                        drawCircle(Color(0xFF00E676).copy(alpha = 0.4f), radius = 6.dp.toPx())
                    }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = mode.liveStatusText,
                style = SciFiTheme.typography.MetricLabel,
                color = Color(0xFF00E676),
                fontSize = 9.sp
            )
        }
    }
}

@Composable
private fun SectionTopBarContent(mode: TopBarMode.Section) {
    val primary = SciFiTheme.extendedColors.primary
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = mode.title,
            style = SciFiTheme.typography.BodyMono.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
            color = Color.White
        )
        if (mode.badge != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                color = (mode.badgeColor ?: primary).copy(alpha = 0.15f),
                shape = RoundedCornerShape(4.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, (mode.badgeColor ?: primary).copy(alpha = 0.5f))
            ) {
                Text(
                    text = mode.badge,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    fontSize = 9.sp,
                    color = mode.badgeColor ?: primary,
                    style = SciFiTheme.typography.BodyMono
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeTopBar() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        BharatSightTopBar(
            mode = TopBarMode.Home(
                userName = "Pavan",
                onMenuClick = {},
                onSettingsClick = {},
                notificationCount = 5
            )
        )
    }
}
