package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarAction
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.microinteraction.crtScanlineOverlay

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val COMPARE = "compare"
    const val UPDATES = "updates"
    const val ASK = "ask"
    const val PROFILE = "profile"
    const val MACRO = "macro"
    const val SECTOR = "sector"
    const val DEMOGRAPHICS = "demographics"
    const val FORECASTER = "forecaster"
    const val SETTINGS = "settings"
    const val GLOBE = "globe"
    const val QUERY = "query"
    const val OBSERVATORY = "observatory"
    const val TRADE = "trade"
    const val MARKET = "market"
    const val STATES = "states"
    const val CHART_DETAIL = "chart_detail/{chartId}"
    
    // New Sectors
    const val AGRICULTURE = "agriculture"
    const val BANKING = "banking"
    const val ENERGY = "energy"
    const val SMART_CITIES = "smart_cities"
    const val STARTUP = "startup"
    const val DEFENCE = "defence"
    const val CLIMATE = "climate"
    const val DIGITAL_ECONOMY = "digital_economy"
    const val EDUCATION = "education"
    const val HEALTHCARE = "healthcare"
    const val REAL_ESTATE = "real_estate"
    const val TOURISM = "tourism"
    const val SPACE_TECH = "space_tech"
    const val GEO_RISK = "geo_risk"
    const val INEQUALITY = "inequality"
    const val LABOUR = "labour"
    const val LOGISTICS = "logistics"
    const val MEDIA = "media"
    const val NATURAL_RESOURCES = "natural_resources"
    const val SOFT_POWER = "soft_power"
}

@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Settings",
                    onBackClick = onBack,
                    actions = listOf(
                        TopBarAction(Icons.Outlined.Info, "Info", { /* Handle Info */ })
                    )
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        GridBackgroundSurface(modifier = Modifier.fillMaxSize().crtScanlineOverlay().padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "ENGINE: TFLite 2.16.1", color = Color(0xFF00E5FF))
                Text(text = "RESOLUTION: PORTRAIT_HUD", color = Color(0xFF39FF14))
                Text(text = "THEME: RETRO_DARK_2075", color = Color(0xFFFF3B30))
            }
        }
    }
}
