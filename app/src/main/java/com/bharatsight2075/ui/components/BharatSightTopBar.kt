package com.bharatsight2075.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

sealed class TopBarMode {
    data class Home(
        val userName: String,
        val onMenuClick: () -> Unit,
        val onSettingsClick: () -> Unit = {},
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

@Composable
fun BharatSightTopBar(
    mode: TopBarMode,
    modifier: Modifier = Modifier
) {
    val extendedColors = SciFiTheme.extendedColors
    val height = when (mode) {
        is TopBarMode.Home -> 60.dp
        is TopBarMode.Section -> 56.dp
    }

    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(300, easing = EaseOutCubic)
        ) + fadeIn(animationSpec = tween(300))
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .drawBehind {
                    val alpha = if (mode is TopBarMode.Home) 0.25f else 0.2f
                    drawLine(
                        color = extendedColors.primary.copy(alpha = alpha),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                },
            color = extendedColors.surface
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (mode is TopBarMode.Home) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        extendedColors.primary.copy(alpha = 0.08f),
                                        Color.Transparent,
                                        extendedColors.accent.copy(alpha = 0.05f)
                                    )
                                )
                            )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (mode) {
                        is TopBarMode.Home -> HomeTopBarContent(mode)
                        is TopBarMode.Section -> SectionTopBarContent(mode)
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.HomeTopBarContent(mode: TopBarMode.Home) {
    val colors = SciFiTheme.extendedColors
    
    // LEFT - Menu
    Box(contentAlignment = Alignment.CenterStart) {
        // Accent Bar
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(4.dp)
                .background(colors.primary)
        )
        IconButton(
            onClick = mode.onMenuClick,
            modifier = Modifier
                .padding(start = 4.dp)
                .size(44.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = colors.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    // CENTER - Greeting
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hi, ${mode.userName} 👋",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = colors.textPrimary,
            textAlign = TextAlign.Center
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.7f,
                targetValue = 1.3f,
                animationSpec = infiniteRepeatable(
                    animation = tween(900),
                    repeatMode = RepeatMode.Reverse
                ), label = "dotScale"
            )
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color(0xFF00E676),
                            radius = (size.width / 2) * scale
                        )
                    }
                    .background(Color(0xFF00E676), CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = mode.liveStatusText,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    letterSpacing = 0.1.sp
                ),
                color = Color(0xFF00E676).copy(alpha = 0.85f)
            )
        }
    }

    // RIGHT - Notifications + Settings
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box {
            IconButton(onClick = { /* Notifications */ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = colors.primary.copy(alpha = 0.8f)
                )
            }
            if (mode.notificationCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                        .size(6.dp)
                        .background(Color.Red, CircleShape)
                )
            }
        }
        IconButton(onClick = mode.onSettingsClick) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = colors.primary.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun RowScope.SectionTopBarContent(mode: TopBarMode.Section) {
    val colors = SciFiTheme.extendedColors
    
    // LEFT - Back
    Box(
        modifier = Modifier
            .size(36.dp)
            .border(1.dp, colors.primary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { mode.onBackClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = colors.primary,
            modifier = Modifier.size(22.dp)
        )
    }

    // CENTER - Title + Badge
    var titleAlpha by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(100)
        titleAlpha = 1f
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = titleAlpha,
        animationSpec = tween(400),
        label = "TitleAlpha"
    )

    Row(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = mode.title,
            style = SciFiTheme.typography.BodyMono.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.alpha(animatedAlpha)
        )
        
        if (mode.badge != null) {
            Spacer(modifier = Modifier.width(8.dp))
            val badgeColor = mode.badgeColor ?: colors.primary
            Box(
                modifier = Modifier
                    .border(1.dp, badgeColor.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                    .background(badgeColor.copy(alpha = 0.12f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = mode.badge,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.1.sp
                    ),
                    color = badgeColor
                )
            }
        }
    }

    // RIGHT - Actions
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (mode.actions.isEmpty()) {
            IconButton(onClick = { /* Share */ }) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share",
                    tint = colors.primary.copy(alpha = 0.7f)
                )
            }
        } else {
            mode.actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = action.contentDesc,
                        tint = colors.primary.copy(alpha = 0.7f)
                    )
                }
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
                notificationCount = 2
            )
        )
    }
}

@Preview
@Composable
fun PreviewSectionTopBar() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Hologram) {
        BharatSightTopBar(
            mode = TopBarMode.Section(
                title = "Macro Overview",
                badge = "LIVE",
                badgeColor = Color(0xFF00E676),
                onBackClick = {}
            )
        )
    }
}
