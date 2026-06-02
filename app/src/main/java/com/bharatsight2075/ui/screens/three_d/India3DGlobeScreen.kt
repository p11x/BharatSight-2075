package com.bharatsight2075.ui.screens.three_d

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
fun India3DGlobeScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "India Economic Map",
                    badge = "36 STATES",
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
                    chartId = "globe_map",
                    navController = navController,
                    title = "INTERACTIVE STATE TOPOGRAPHY"
                ) {
                    IndiaMapCanvas(
                        modifier = Modifier.height(400.dp),
                        onStateDrillDown = { /* Navigate */ }
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_gsdp_ranking",
                    navController = navController,
                    title = "TOP 10 STATES BY GSDP (₹L Cr)"
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
                    chartId = "globe_fdi_flow",
                    navController = navController,
                    title = "FDI SOURCE SECTOR FLOW"
                ) {
                    SankeyFlowChart(
                        nodes = emptyList(),
                        flows = emptyList(),
                        modifier = Modifier.height(280.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_trade_particles",
                    navController = navController,
                    title = "REAL-TIME EXPORT VOLUME (PARTICLES)"
                ) {
                    ParticleFlowCanvas(
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_growth_pop",
                    navController = navController,
                    title = "GSDP GROWTH vs POPULATION"
                ) {
                    ScatterWithTrendLine(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "globe_regional_donut",
                        navController = navController,
                        title = "REGIONAL GDP %", 
                        modifier = Modifier.weight(1f)
                    ) {
                        GradientDonutChart(
                            values = emptyList(),
                            brushes = emptyList(),
                            label = "REGIONS",
                            modifier = Modifier.height(180.dp)
                        )
                    }
                    DashCard(
                        chartId = "globe_infra_radar",
                        navController = navController,
                        title = "INFRA RADAR", 
                        modifier = Modifier.weight(1f)
                    ) {
                        RadarPolygonChart(
                            data = emptyList(),
                            labels = emptyList(),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            item {
                DashCard(
                    chartId = "globe_urban_mirror",
                    navController = navController,
                    title = "URBAN vs RURAL SHARE (TOP STATES)"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_literacy_heatmap",
                    navController = navController,
                    title = "STATE LITERACY & HDI MATRIX"
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(260.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_gsdp_waterfall",
                    navController = navController,
                    title = "REVENUE SOURCES WATERFALL"
                ) {
                    WaterfallBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_pop_wave",
                    navController = navController,
                    title = "POPULATION DENSITY DISTRIBUTION"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(160.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_coastal_rings",
                    navController = navController,
                    title = "COASTAL TRADE RINGS (VOL)"
                ) {
                    RingProgressCluster(
                        rings = emptyList(),
                        centerStat = "84%",
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_industry_polar",
                    navController = navController,
                    title = "STATE INDUSTRIAL COMPOSITION"
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
                    chartId = "globe_formation_timeline",
                    navController = navController,
                    title = "ECONOMIC REORGANIZATION TIMELINE"
                ) {
                    TimelineEventChart(
                        events = emptyList(),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_gsdp_bubbles",
                    navController = navController,
                    title = "GSDP CONCENTRATION (BUBBLE MAP)"
                ) {
                    BubbleScatterChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "globe_infra_progress",
                    navController = navController,
                    title = "INFRASTRUCTURE COMPLETION INDEX"
                ) {
                    HorizontalProgressBars(
                        items = emptyList(),
                        modifier = Modifier.height(120.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
