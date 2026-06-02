package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.DashCard
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.components.TwoColumnRow
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun MacroOverviewScreen(
    navController: NavController,
    marketData: List<com.bharatsight2075.service.LiveMarketData> = emptyList(),
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Macro Overview",
                    badge = "LIVE",
                    badgeColor = Color(0xFF00E676),
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
                    chartId = "macro_gdp_hero",
                    navController = navController,
                    title = "Core Economic Indicator",
                    showLiveDot = true
                ) {
                    GlowingNumberTicker(
                        value = 37.00f,
                        unit = "USD TRILLION",
                        delta = "▲ 8.1% YoY"
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_gdp_projection",
                    navController = navController,
                    title = "GDP 50-YEAR PROJECTION"
                ) {
                    GradientAreaChart(
                        data = emptyList(), // Use mock
                        modifier = Modifier.height(180.dp),
                        strokeColor = SciFiTheme.extendedColors.primary
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "macro_indicator_rings",
                        navController = navController,
                        title = "STABILITY RINGS",
                        modifier = Modifier.weight(1f)
                    ) {
                        RingProgressCluster(
                            rings = emptyList(),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                    DashCard(
                        chartId = "macro_hdi_gauge",
                        navController = navController,
                        title = "HDI GAUGE",
                        modifier = Modifier.weight(1f)
                    ) {
                        HalfDonutGauge(
                            value = 0.644f,
                            max = 1.0f,
                            label = "HDI INDEX",
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            item {
                DashCard(
                    chartId = "macro_sector_donut",
                    navController = navController,
                    title = "SECTOR COMPOSITION"
                ) {
                    GradientDonutChart(
                        values = emptyList(),
                        brushes = emptyList(),
                        label = "GVA SHARE",
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_growth_bar",
                    navController = navController,
                    title = "YOY GROWTH (2015-25)"
                ) {
                    GradientBarChart(
                        data = emptyList(),
                        labels = emptyList(),
                        modifier = Modifier.height(160.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_trade_mirror",
                    navController = navController,
                    title = "TRADE BALANCE (MONTHLY)"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_inflation_multi",
                    navController = navController,
                    title = "INFLATION VARIANCE (CPI/WPI)"
                ) {
                    MultiLineChart(
                        data = emptyList(),
                        colors = listOf(SciFiTheme.extendedColors.accent, SciFiTheme.extendedColors.primary),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "macro_unemployment_speedo",
                        navController = navController,
                        title = "UNEMPLOYMENT",
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(
                            value = 7.8f,
                            max = 15f,
                            label = "RATE %",
                            modifier = Modifier.height(180.dp)
                        )
                    }
                    DashCard(
                        chartId = "macro_credit_wave",
                        navController = navController,
                        title = "CREDIT WAVE",
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
                    chartId = "macro_repo_candles",
                    navController = navController,
                    title = "REPO RATE FLUCTUATION"
                ) {
                    CandlestickChart(
                        data = emptyList(),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_rank_heatmap",
                    navController = navController,
                    title = "GLOBAL MACRO RANKING"
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_remit_sankey",
                    navController = navController,
                    title = "REMITTANCE SOURCE FLOW"
                ) {
                    SankeyFlowChart(
                        nodes = emptyList(),
                        flows = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_money_stacked",
                    navController = navController,
                    title = "M3 MONEY SUPPLY COMPONENTS"
                ) {
                    StackedAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_recovery_bubbles",
                    navController = navController,
                    title = "SECTOR RECOVERY DYNAMICS"
                ) {
                    BubbleScatterChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_fiscal_progress",
                    navController = navController,
                    title = "FISCAL REFORM PROGRESS"
                ) {
                    HorizontalProgressBars(
                        items = emptyList(),
                        modifier = Modifier.height(120.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_sector_venn",
                    navController = navController,
                    title = "SECTORAL SYNERGY MAP"
                ) {
                    VennDiagramChart(
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_milestones_timeline",
                    navController = navController,
                    title = "HISTORICAL MACRO MILESTONES"
                ) {
                    TimelineEventChart(
                        events = emptyList(),
                        modifier = Modifier.height(140.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
