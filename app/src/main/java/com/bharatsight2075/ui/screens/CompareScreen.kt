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
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun CompareScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Global Economy Compare",
                    badge = "LIVE",
                    badgeColor = extendedColors.primary,
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
                    chartId = "compare_gdp",
                    navController = navController,
                    title = "WORLD GDP COMPARISON (\$T)"
                ) {
                    GradientBarChart(
                        data = emptyList(),
                        labels = listOf("IND", "USA", "CHN", "DEU", "JPN", "GBR"),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "compare_growth_scatter",
                    navController = navController,
                    title = "GROWTH vs INFLATION (G6)"
                ) {
                    ScatterWithTrendLine(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "compare_radar",
                    navController = navController,
                    title = "GLOBAL MACRO PERFORMANCE"
                ) {
                    RadarPolygonChart(
                        data = emptyList(),
                        labels = emptyList(),
                        modifier = Modifier.height(300.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "compare_inflation_mirror",
                    navController = navController,
                    title = "GROWTH vs INFLATION MIRROR"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "compare_hdi_rings",
                    navController = navController,
                    title = "HDI PEER CONCENTRIC RINGS"
                ) {
                    RingProgressCluster(
                        rings = emptyList(),
                        centerStat = "0.7",
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "compare_fx_polar",
                    navController = navController,
                    title = "FX RESERVE COMPOSITION"
                ) {
                    PolarAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(260.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "compare_mkt_cap_treemap",
                    navController = navController,
                    title = "STOCK EXCHANGE MARKET CAP"
                ) {
                    TreemapChart(
                        weights = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(300.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "compare_comp_speedo",
                        navController = navController,
                        title = "COMPETITORS", 
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(value = 65.4f, max = 100f, label = "RANK INDEX", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "compare_recovery_waterfall",
                        navController = navController,
                        title = "RECOVERY Δ", 
                        modifier = Modifier.weight(1f)
                    ) {
                        WaterfallBarChart(
                            data = emptyList(),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            item {
                DashCard(
                    chartId = "compare_export_venn",
                    navController = navController,
                    title = "PRODUCT CATEGORY OVERLAP"
                ) {
                    VennDiagramChart(modifier = Modifier.height(280.dp))
                }
            }

            item {
                DashCard(
                    chartId = "compare_g20_timeline",
                    navController = navController,
                    title = "G20 ECONOMIC SUMMIT MILSTONES"
                ) {
                    TimelineEventChart(
                        events = emptyList(),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "compare_sentiment_wave",
                    navController = navController,
                    title = "MARKET SENTIMENT COHERENCE"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(150.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
