package com.bharatsight2075.ui.screens.three_d

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*
import com.bharatsight2075.ui.visualization.charts.SankeyFlow
import com.bharatsight2075.ui.visualization.charts.SankeyNode
import com.bharatsight2075.ui.visualization.charts.SankeyFlowChart
import com.bharatsight2075.ui.visualization.charts.RingData
import com.bharatsight2075.ui.visualization.charts.MirrorData
import com.bharatsight2075.ui.visualization.charts.BubbleData
import com.bharatsight2075.ui.visualization.charts.TimelineEvent

@Composable
fun India3DGlobeScreen(
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
            // 1. Interactive India Map (Heatmap Cell Logic)
            item {
                key("india_topography") {
                    DashCard(title = "INTERACTIVE STATE TOPOGRAPHY") {
                        IndiaMapCanvas(
                            modifier = Modifier.height(400.dp),
                            onStateDrillDown = { /* Navigate */ }
                        )
                    }
                }
            }

            // 2. State GSDP Bars (C05)
            item {
                key("gsdp_ranking") {
                    DashCard(title = "TOP 10 STATES BY GSDP (₹L Cr)") {
                        GradientBarChart(
                            data = listOf(38.8f, 28.3f, 25.6f, 25.4f, 15.2f, 14.1f, 12.8f, 11.2f, 9.4f, 8.1f),
                            labels = emptyList(),
                            modifier = Modifier.height(200.dp),
                            brush = GradPalette.TEAL_PURPLE
                        )
                    }
                }
            }

            // 3. FDI Flow Sankey (C14)
            item {
                key("fdi_flow") {
                    DashCard(title = "FDI SOURCE SECTOR FLOW") {
                        SankeyFlowChart(
                            nodes = listOf(
                                SankeyNode("SINGAPORE", 100f, Color(0xFF00F5FF)),
                                SankeyNode("MAH", 120f, Color(0xFFFF6B35)),
                                SankeyNode("KAR", 80f, Color(0xFF39FF14)),
                                SankeyNode("GUJ", 90f, Color(0xFFFFD600))
                            ),
                            flows = listOf(
                                SankeyFlow(0, 1, 60f),
                                SankeyFlow(0, 2, 40f),
                                SankeyFlow(0, 3, 30f)
                            ),
                            modifier = Modifier.height(280.dp)
                        )
                    }
                }
            }

            // 4. Trade Arc Particles (C21)
            item {
                key("trade_particles") {
                    DashCard(title = "REAL-TIME EXPORT VOLUME (PARTICLES)") {
                        ParticleFlowCanvas(
                            dataSize = 20,
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 5. State Growth Scatter (C17)
            item {
                key("growth_pop_scatter") {
                    DashCard(title = "GSDP GROWTH vs POPULATION") {
                        ScatterWithTrendLine(
                            data = listOf(
                                androidx.compose.ui.geometry.Offset(10f, 20f),
                                androidx.compose.ui.geometry.Offset(25f, 40f),
                                androidx.compose.ui.geometry.Offset(38f, 35f),
                                androidx.compose.ui.geometry.Offset(52f, 60f),
                                androidx.compose.ui.geometry.Offset(70f, 55f)
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 6. Regional Donut (C03) + Indicators
            item {
                key("regional_breakdown") {
                    TwoColumnRow {
                        DashCard(title = "REGIONAL GDP %", modifier = Modifier.weight(1f)) {
                            GradientDonutChart(
                                values = listOf(30f, 25f, 15f, 20f, 10f),
                                brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE, GradPalette.PURPLE_BLUE),
                                label = "REGIONS",
                                modifier = Modifier.height(180.dp)
                            )
                        }
                        DashCard(title = "INFRA RADAR", modifier = Modifier.weight(1f)) {
                            RadarPolygonChart(
                                data = listOf(0.8f, 0.4f, 0.6f, 0.9f, 0.5f),
                                labels = emptyList(),
                                modifier = Modifier.height(180.dp),
                                brush = GradPalette.PINK_PURPLE
                            )
                        }
                    }
                }
            }

            // 7. Urban-Rural Mirror (C22)
            item {
                key("urban_rural_state") {
                    DashCard(title = "URBAN vs RURAL SHARE (TOP STATES)") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(70f, 30f, "MAH"),
                                MirrorData(65f, 35f, "TN"),
                                MirrorData(58f, 42f, "GUJ"),
                                MirrorData(45f, 55f, "UP")
                            ),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 8. Literacy Heatmap (C15)
            item {
                key("literacy_matrix") {
                    DashCard(title = "STATE LITERACY & HDI MATRIX") {
                        HeatmapGridChart(
                            data = List(8) { List(10) { (30..100).random() / 100f } },
                            modifier = Modifier.height(260.dp),
                            brush = GradPalette.GREEN_TEAL
                        )
                    }
                }
            }

            // 9. State Revenue Waterfall (C19)
            item {
                key("state_waterfall") {
                    DashCard(title = "REVENUE SOURCES WATERFALL") {
                        WaterfallBarChart(
                            data = listOf(60f, 25f, 15f, -40f, -30f, 20f),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 10. Population Density Wave (C11)
            item {
                key("density_wave") {
                    DashCard(title = "POPULATION DENSITY DISTRIBUTION") {
                        WaveformChart(
                            modifier = Modifier.height(160.dp),
                            brush = GradPalette.CYAN_WHITE
                        )
                    }
                }
            }

            // 11. Coastal Trade Ring (C12)
            item {
                key("coastal_rings") {
                    DashCard(title = "COASTAL TRADE RINGS (VOL)") {
                        RingProgressCluster(
                            rings = listOf(
                                RingData(0.92f, "WEST", GradPalette.TEAL_PURPLE),
                                RingData(0.78f, "SOUTH", GradPalette.ORANGE_PINK),
                                RingData(0.45f, "EAST", GradPalette.GREEN_TEAL)
                            ),
                            centerStat = "84%",
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 12. State Industry Polar (C18)
            item {
                key("industry_polar") {
                    DashCard(title = "STATE INDUSTRIAL COMPOSITION") {
                        PolarAreaChart(
                            data = listOf(40f, 30f, 20f, 60f, 80f, 50f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE, GradPalette.PURPLE_BLUE, GradPalette.PINK_PURPLE),
                            modifier = Modifier.height(250.dp)
                        )
                    }
                }
            }

            // 13. State Formation Timeline (C23)
            item {
                key("state_timeline") {
                    DashCard(title = "ECONOMIC REORGANIZATION TIMELINE") {
                        TimelineEventChart(
                            events = listOf(
                                TimelineEvent(1956, "STATE REORG", true),
                                TimelineEvent(2000, "NEW STATES", false),
                                TimelineEvent(2014, "TELANGANA", true),
                                TimelineEvent(2025, "SMART HUBS", false)
                            ),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            // 14. Bubble Map Overlay (C08)
            item {
                key("bubble_map") {
                    DashCard(title = "GSDP CONCENTRATION (BUBBLE MAP)") {
                        BubbleScatterChart(
                            data = listOf(
                                BubbleData(10f, 20f, 1.2f, "MUM"),
                                BubbleData(30f, 45f, 0.9f, "BLR"),
                                BubbleData(55f, 70f, 0.8f, "DEL"),
                                BubbleData(75f, 30f, 0.7f, "CHE")
                            ),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 15. Policy Milestone Progress (C06)
            item {
                key("policy_bars") {
                    DashCard(title = "INFRASTRUCTURE COMPLETION INDEX") {
                        HorizontalProgressBars(
                            items = listOf(
                                "HIGHWAYS" to 0.92f,
                                "DIGITAL GRID" to 0.75f,
                                "RE WATER" to 0.58f
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIndia3DGlobeScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        India3DGlobeScreen(onBack = {})
    }
}
