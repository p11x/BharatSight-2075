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
fun SectorDeepDiveScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Sector Analysis",
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
                    chartId = "sector_treemap",
                    navController = navController,
                    title = "SUB-SECTOR MARKET DENSITY"
                ) {
                    TreemapChart(
                        weights = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(320.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_area_trend",
                    navController = navController,
                    title = "GVA CONTRIBUTION TREND (%)"
                ) {
                    StackedAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_radar",
                    navController = navController,
                    title = "CORE PERFORMANCE ATTRIBUTES"
                ) {
                    RadarPolygonChart(
                        data = emptyList(),
                        labels = listOf("GROWTH", "MARGIN", "ESG", "DEBT", "CAPEX", "FDI"),
                        modifier = Modifier.height(320.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_productivity",
                    navController = navController,
                    title = "OUTPUT PER WORKER vs AVG WAGE"
                ) {
                    ScatterWithTrendLine(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "sector_market_share",
                        navController = navController,
                        title = "MARKET SHARE", 
                        modifier = Modifier.weight(1f)
                    ) {
                        HalfDonutGauge(value = 53.2f, max = 100f, label = "PERCENT", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "sector_utilization",
                        navController = navController,
                        title = "UTILIZATION", 
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(value = 78.5f, max = 100f, label = "CAPACITY", modifier = Modifier.height(180.dp))
                    }
                }
            }

            item {
                DashCard(
                    chartId = "sector_subsector_growth",
                    navController = navController,
                    title = "SUB-SECTOR YOY GROWTH (%)"
                ) {
                    GradientBarChart(
                        data = emptyList(),
                        labels = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_revenue_mix",
                    navController = navController,
                    title = "DOMESTIC vs EXPORT REVENUE"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_price_wave",
                    navController = navController,
                    title = "SECTORAL WPI VOLATILITY"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(150.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_revenue_waterfall",
                    navController = navController,
                    title = "REVENUE COMPONENT WATERFALL"
                ) {
                    WaterfallBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_index_candles",
                    navController = navController,
                    title = "SECTOR INDEX FLUCTUATION"
                ) {
                    CandlestickChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_fdi_matrix",
                    navController = navController,
                    title = "HISTORICAL FDI INFLOW MATRIX"
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_employment_polar",
                    navController = navController,
                    title = "WORKFORCE DISTRIBUTION"
                ) {
                    PolarAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(250.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_strategic_investments",
                    navController = navController,
                    title = "MAJOR STRATEGIC INVESTMENTS"
                ) {
                    TimelineEventChart(
                        events = emptyList(),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_global_rankings",
                    navController = navController,
                    title = "GLOBAL SUB-SECTOR RANKINGS"
                ) {
                    RingProgressCluster(
                        rings = emptyList(),
                        centerStat = "#02",
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "sector_synergy_venn",
                    navController = navController,
                    title = "INTER-SECTORAL SYNERGY MAP"
                ) {
                    VennDiagramChart(modifier = Modifier.height(280.dp))
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
