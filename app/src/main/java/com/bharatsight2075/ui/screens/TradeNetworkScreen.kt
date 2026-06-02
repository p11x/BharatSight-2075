package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun TradeNetworkScreen(
    navController: NavController,
    onBack: () -> Unit
) {
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
                DashCard(
                    chartId = "trade_flow",
                    navController = navController,
                    title = "TRADE NETWORK PARTICLE DYNAMICS"
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
                    title = "EXPORT vs IMPORT VOLUME ($)"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "trade_export_share",
                    navController = navController,
                    title = "COMMODITY EXPORT SHARE (%)"
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
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(value = 4.2f, max = 5f, label = "LPI SCORE", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "trade_port",
                        navController = navController,
                        title = "PORT TRAFFIC", 
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
