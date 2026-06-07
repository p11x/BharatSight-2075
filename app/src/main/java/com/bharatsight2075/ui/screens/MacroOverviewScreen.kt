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
class MacroOverviewViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("macro_overview")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun MacroOverviewScreen(
    navController: NavController,
    viewModel: MacroOverviewViewModel = hiltViewModel(),
    marketData: List<com.bharatsight2075.service.LiveMarketData> = emptyList(),
    onBack: () -> Unit
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()

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
                HeroStatsRow(
                    chartId = "gdp_hero",
                    navController = navController,
                    stats = uiState.heroStats,
                    description = "Live GDP, inflation & key national economic indicators"
                )
            }

            item(key = "gdp_projection") {
                DashCard(
                    chartId = "gdp_projection",
                    navController = navController,
                    title = "GDP 50-YEAR PROJECTION",
                    description = "50-year GDP trajectory from 2026 to 2075 under base scenario",
                    cardIndex = 1
                ) {
                    GradientAreaChart(
                        data = emptyList(), // Use mock
                        modifier = Modifier.height(180.dp),
                        strokeColor = SciFiTheme.extendedColors.primary
                    )
                }
            }

            item(key = "stability_rings_hdi") {
                TwoColumnRow {
                    DashCard(
                        chartId = "stability_rings",
                        navController = navController,
                        title = "STABILITY RINGS",
                        description = "Inflation, repo rate & fiscal deficit ring gauges",
                        modifier = Modifier.weight(1f),
                        cardIndex = 2
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
                        modifier = Modifier.weight(1f),
                        cardIndex = 3
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

            item(key = "sector_donut") {
                DashCard(
                    chartId = "sector_donut",
                    navController = navController,
                    title = "SECTOR COMPOSITION",
                    description = "Agriculture · Industry · Services share of GDP",
                    cardIndex = 4
                ) {
                    GradientDonutChart(
                        values = emptyList(),
                        label = "GVA SHARE",
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "growth_bar",
                    navController = navController,
                    title = "YOY GROWTH (2015-25)",
                    description = "Year-on-year GDP growth rate 2010–2025",
                    cardIndex = 5
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
                    chartId = "trade_mirror",
                    navController = navController,
                    title = "TRADE BALANCE (MONTHLY)",
                    description = "Monthly exports vs imports — trade balance analysis",
                    cardIndex = 6
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "inflation_lines",
                    navController = navController,
                    title = "INFLATION VARIANCE (CPI/WPI)",
                    description = "CPI, WPI and core inflation 24-month comparison",
                    cardIndex = 7
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
                        chartId = "employment_gauge",
                        navController = navController,
                        title = "UNEMPLOYMENT",
                        description = "Unemployment rate on 0–15% speedometer scale",
                        modifier = Modifier.weight(1f),
                        cardIndex = 8
                    ) {
                        SpeedometerGauge(
                            value = 7.8f,
                            max = 15f,
                            label = "RATE %",
                            modifier = Modifier.height(180.dp)
                        )
                    }
                    DashCard(
                        chartId = "credit_wave",
                        navController = navController,
                        title = "CREDIT WAVE",
                        description = "Animated credit growth rate wave — banking sector",
                        modifier = Modifier.weight(1f),
                        cardIndex = 9
                    ) {
                        WaveformChart(
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            item {
                DashCard(
                    chartId = "repo_candle",
                    navController = navController,
                    title = "REPO RATE FLUCTUATION",
                    description = "RBI repo rate OHLC history — monetary policy timeline",
                    cardIndex = 10
                ) {
                    CandlestickChart(
                        data = emptyList(),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "macro_heatmap",
                    navController = navController,
                    title = "GLOBAL MACRO RANKING",
                    description = "India vs G20 — 8 macro metrics performance matrix",
                    cardIndex = 11
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "remittance_sankey",
                    navController = navController,
                    title = "REMITTANCE SOURCE FLOW",
                    description = "Top 5 source countries → India remittance flow bands",
                    cardIndex = 12
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
                    chartId = "m3_stacked",
                    navController = navController,
                    title = "M3 MONEY SUPPLY COMPONENTS",
                    description = "M1 + M2 money supply components stacked over time",
                    cardIndex = 13
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
                    chartId = "bubble_sectors",
                    navController = navController,
                    title = "SECTOR RECOVERY DYNAMICS",
                    description = "Sectors: x=growth, y=GDP contribution, size=employment",
                    cardIndex = 14
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
                    title = "FISCAL REFORM PROGRESS",
                    cardIndex = 15
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
                    title = "SECTORAL SYNERGY MAP",
                    cardIndex = 16
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
                    title = "HISTORICAL MACRO MILESTONES",
                    cardIndex = 17
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
