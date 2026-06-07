package com.bharatsight2075.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val extendedColors = SciFiTheme.extendedColors

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Profile",
                    onBackClick = { /* Tab root */ }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { HeroProfileCard(profile, extendedColors) }
            
            item { 
                StatsGrid(profile, extendedColors)
            }

            item { 
                SectionHeader("OPERATIONAL SETTINGS", extendedColors)
            }

            item { 
                SettingsGroup(extendedColors)
            }

            item {
                Button(
                    onClick = { /* Sign Out */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = extendedColors.negative.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, extendedColors.negative.copy(alpha = 0.3f))
                ) {
                    Icon(Icons.Outlined.Logout, contentDescription = null, tint = extendedColors.negative)
                    Spacer(Modifier.width(12.dp))
                    Text("TERMINATE SESSION", color = extendedColors.negative, fontWeight = FontWeight.Bold)
                }
            }

            item { Spacer(Modifier.height(100.dp)) }
        }
    }
}

@Composable
private fun HeroProfileCard(profile: UserProfile, colors: SciFiTheme.SciFiColors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.primary.copy(alpha = 0.05f))
            .border(1.dp, colors.primary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(colors.accent.copy(alpha = 0.2f))
                    .border(1.dp, colors.accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Person, contentDescription = null, tint = colors.accent, modifier = Modifier.size(32.dp))
            }
            Spacer(Modifier.width(20.dp))
            Column {
                Text(profile.name, style = SciFiTheme.typography.HeroNumber.copy(fontSize = 18.sp), color = Color.White)
                Text(profile.rank, fontSize = 10.sp, color = colors.accent, fontWeight = FontWeight.Bold)
                Surface(
                    modifier = Modifier.padding(top = 4.dp),
                    color = colors.positive.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("SEC_LEVEL: ALPHA-9", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 8.sp, color = colors.positive)
                }
            }
        }
    }
}

@Composable
private fun StatsGrid(profile: UserProfile, colors: SciFiTheme.SciFiColors) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        StatItem("ACCURACY", "${profile.accuracy}%", colors.primary, Modifier.weight(1f))
        StatItem("SCENARIOS", profile.scenariosSaved.toString(), colors.accent, Modifier.weight(1f))
        StatItem("WATCHLIST", profile.watchlistedStates.toString(), colors.positive, Modifier.weight(1f))
    }
}

@Composable
private fun StatItem(label: String, value: String, color: Color, modifier: Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.3f))
            .border(1.dp, color.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, style = SciFiTheme.typography.HeroNumber.copy(fontSize = 16.sp), color = color)
        Text(label, fontSize = 8.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SectionHeader(title: String, colors: SciFiTheme.SciFiColors) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = colors.textDisabled, letterSpacing = 1.sp)
        Spacer(Modifier.width(8.dp))
        HorizontalDivider(modifier = Modifier.weight(1f), color = colors.textDisabled.copy(alpha = 0.2f))
    }
}

@Composable
private fun SettingsGroup(colors: SciFiTheme.SciFiColors) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.3f))
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
    ) {
        SettingsRow(Icons.Outlined.Palette, "THEME INTERFACE", "CYBERPUNK (DEFAULT)", colors)
        HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
        SettingsRow(Icons.Outlined.Notifications, "HUD ALERTS", "ENABLED", colors)
        HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
        SettingsRow(Icons.Outlined.Language, "LINGUISTIC DATA", "ENGLISH (UK)", colors)
    }
}

@Composable
private fun SettingsRow(icon: ImageVector, title: String, value: String, colors: SciFiTheme.SciFiColors) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable {}.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = colors.primary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text(value, fontSize = 10.sp, color = colors.textSecondary)
        }
        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = colors.textDisabled)
    }
}
