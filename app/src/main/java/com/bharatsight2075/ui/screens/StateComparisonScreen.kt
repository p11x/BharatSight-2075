package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bharatsight2075.data.local.StateEconomyEntity
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun StateComparisonScreen(
    navController: NavController,
    states: List<StateEconomyEntity> = emptyList(),
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "State Deep Dive",
                    badge = "36 STATES",
                    badgeColor = Color(0xFFB39DDB),
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
                    chartId = "state_gsdp_ranking",
                    navController = navController,
                    title = "GSDP COMPARATIVE RANKING"
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
                    chartId = "state_growth_scatter",
                    navController = navController,
                    title = "GROWTH RATE vs BASE ECONOMY"
                ) {
                    ScatterWithTrendLine(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_hdi_matrix",
                    navController = navController,
                    title = "STATE HDI PARAMETER MATRIX"
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_per_capita",
                    navController = navController,
                    title = "PER-CAPITA GSDP DISTRIBUTION"
                ) {
                    GradientDonutChart(
                        values = emptyList(),
                        brushes = emptyList(),
                        label = "DISTRIBUTION",
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_radar",
                    navController = navController,
                    title = "SELECTED STATE vs NATIONAL AVG"
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
                    chartId = "state_emp_polar",
                    navController = navController,
                    title = "SECTORAL EMPLOYMENT (POLAR)"
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
                    chartId = "state_urban_mirror",
                    navController = navController,
                    title = "URBAN vs RURAL GSDP CONTRIBUTION"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_revenue_waterfall",
                    navController = navController,
                    title = "OWN TAX vs CENTRAL TRANSFERS"
                ) {
                    WaterfallBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_infra_wave",
                    navController = navController,
                    title = "INFRASTRUCTURE INDEX SATURATION"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(160.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_fdi_bubbles",
                    navController = navController,
                    title = "FDI INFLOW vs EASE OF BUSINESS"
                ) {
                    BubbleScatterChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_milestones",
                    navController = navController,
                    title = "STATE ECONOMIC MILESTONES"
                ) {
                    TimelineEventChart(
                        events = emptyList(),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_gsdp_trends",
                    navController = navController,
                    title = "TOP 5 STATES GSDP (2000-25)"
                ) {
                    MultiLineChart(
                        data = emptyList(),
                        colors = listOf(extendedColors.primary, extendedColors.accent, Color(0xFF00E676)),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "state_workforce_venn",
                    navController = navController,
                    title = "SECTORAL WORKFORCE OVERLAP"
                ) {
                    VennDiagramChart(modifier = Modifier.height(280.dp))
                }
            }

            item {
                DashCard(
                    chartId = "state_literacy_speedo",
                    navController = navController,
                    title = "STATE LITERACY PERFORMANCE"
                ) {
                    SpeedometerGauge(value = 82.4f, max = 100f, label = "PERCENT", modifier = Modifier.height(200.dp))
                }
            }

            item {
                DashCard(
                    chartId = "state_growth_area",
                    navController = navController,
                    title = "GSDP GROWTH COMPOSITION (%)"
                ) {
                    StackedAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
