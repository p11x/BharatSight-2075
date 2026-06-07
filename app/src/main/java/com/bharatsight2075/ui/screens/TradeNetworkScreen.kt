package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.SectionDefaultData
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TradeViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("trade_network")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun TradeNetworkScreen(
    navController: NavController,
    viewModel: TradeViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    val extendedColors = SciFiTheme.extendedColors
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Global Trade Network",
                    badge = "30 PARTNERS",
                    badgeColor = extendedColors.accent,
                    onBackClick = onBack
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                HeroStatsRow(
                    chartId = "trade_hero",
                    navController = navController,
                    stats = uiState.heroStats,
                    description = "Global trade volume, export share and partner dynamics"
                )
            }
            item {
                DashCard(
                    chartId = "trade_flow",
                    navController = navController,
                    title = "TRADE NETWORK PARTICLE DYNAMICS",
                    description = "Animated global trade flow particle visualization"
                ) {
                    ParticleFlowCanvas(
                        modifier = Modifier.height(380.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "trade_ranking",
                    navController = navController,
                    title = "TOP TRADING PARTNERS (VOL)"
                ) {
                    GradientBarChart(
                        data = emptyList(),
                        labels = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "trade_mirror",
                    navController = navController,
                    title = "EXPORT vs IMPORT VOLUME ($)",
                    description = "Monthly exports vs imports — trade balance analysis"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "export_polar",
                    navController = navController,
                    title = "COMMODITY EXPORT SHARE (%)",
                    description = "Rice, wheat, spices, cotton, sugar export share"
                ) {
                    PolarAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(260.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "trade_lpi",
                        navController = navController,
                        title = "LOGISTICS INDEX", 
                        description = "Logistics Performance Index (LPI) comparative score",
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(value = 4.2f, max = 5f, label = "LPI SCORE", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "port_candle",
                        navController = navController,
                        title = "PORT TRAFFIC", 
                        description = "Major port traffic OHLC monthly",
                        modifier = Modifier.weight(1f)
                    ) {
                        WaveformChart(
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            item {
                DashCard(
                    chartId = "trade_corridors",
                    navController = navController,
                    title = "TRADE CORRIDOR FLOWS"
                ) {
                    SankeyFlowChart(
                        nodes = emptyList(),
                        flows = emptyList(),
                        modifier = Modifier.height(300.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "trade_waterfall",
                    navController = navController,
                    title = "CUMULATIVE TRADE SURPLUS/DEFICIT"
                ) {
                    WaterfallBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "trade_forex",
                    navController = navController,
                    title = "FOREX EARNINGS FROM TRADE"
                ) {
                    GradientAreaChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp),
                        strokeColor = Color(0xFF00E676)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
