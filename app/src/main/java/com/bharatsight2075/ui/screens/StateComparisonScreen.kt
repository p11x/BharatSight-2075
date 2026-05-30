package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.data.local.StateEconomyEntity
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun StateComparisonScreen(
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
            // 1. GSDP Comparison (C05)
            item {
                key("gsdp_ranking") {
                    DashCard(title = "GSDP COMPARATIVE RANKING") {
                        GradientBarChart(
                            data = listOf(38f, 28f, 25f, 25f, 15f, 14f, 12f, 11f, 9f, 8f),
                            labels = emptyList(),
                            modifier = Modifier.height(220.dp),
                            brush = GradPalette.TEAL_PURPLE
                        )
                    }
                }
            }

            // 2. Growth vs Base Scatter (C17)
            item {
                key("growth_base_scatter") {
                    DashCard(title = "GROWTH RATE vs BASE ECONOMY") {
                        ScatterWithTrendLine(
                            data = listOf(
                                androidx.compose.ui.geometry.Offset(10f, 8f),
                                androidx.compose.ui.geometry.Offset(25f, 9f),
                                androidx.compose.ui.geometry.Offset(38f, 7f)
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 3. HDI Heatmap (C15)
            item {
                key("hdi_matrix") {
                    DashCard(title = "STATE HDI PARAMETER MATRIX") {
                        HeatmapGridChart(
                            data = List(10) { List(6) { (40..100).random() / 100f } },
                            modifier = Modifier.height(240.dp),
                            brush = GradPalette.GREEN_TEAL
                        )
                    }
                }
            }

            // 4. Per-capita Donut (C03)
            item {
                key("per_capita_share") {
                    DashCard(title = "PER-CAPITA GSDP DISTRIBUTION") {
                        GradientDonutChart(
                            values = listOf(20f, 20f, 20f, 20f, 20f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE, GradPalette.PURPLE_BLUE),
                            label = "DISTRIBUTION",
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 5. State Radar (C09)
            item {
                key("state_radar_multi") {
                    DashCard(title = "SELECTED STATE vs NATIONAL AVG") {
                        RadarPolygonChart(
                            data = listOf(0.8f, 0.9f, 0.4f, 0.6f, 0.7f),
                            labels = emptyList(),
                            modifier = Modifier.height(300.dp),
                            brush = GradPalette.PINK_PURPLE
                        )
                    }
                }
            }

            // 6. Employment Polar (C18)
            item {
                key("emp_polar") {
                    DashCard(title = "SECTORAL EMPLOYMENT (POLAR)") {
                        PolarAreaChart(
                            data = listOf(60f, 40f, 50f, 30f, 20f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE, GradPalette.PURPLE_BLUE),
                            modifier = Modifier.height(250.dp)
                        )
                    }
                }
            }

            // 7. Urban-Rural Mirror (C22)
            item {
                key("urban_rural_mirror") {
                    DashCard(title = "URBAN vs RURAL GSDP CONTRIBUTION") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(70f, 30f, "MH"),
                                MirrorData(65f, 35f, "TN"),
                                MirrorData(58f, 42f, "GJ")
                            ),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 8. Revenue Waterfall (C19)
            item {
                key("revenue_water") {
                    DashCard(title = "OWN TAX vs CENTRAL TRANSFERS") {
                        WaterfallBarChart(
                            data = listOf(100f, 40f, -80f, -20f, 40f),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 9. Infrastructure Wave (C11)
            item {
                key("infra_wave") {
                    DashCard(title = "INFRASTRUCTURE INDEX SATURATION") {
                        WaveformChart(
                            modifier = Modifier.height(160.dp),
                            brush = GradPalette.YELLOW_ORANGE
                        )
                    }
                }
            }

            // 10. FDI State Bubbles (C08)
            item {
                key("fdi_bubbles") {
                    DashCard(title = "FDI INFLOW vs EASE OF BUSINESS") {
                        BubbleScatterChart(
                            data = listOf(
                                BubbleData(20f, 80f, 1.2f, "MH"),
                                BubbleData(40f, 75f, 0.9f, "TN"),
                                BubbleData(60f, 90f, 1.1f, "GJ")
                            ),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 11. State Timeline (C23)
            item {
                key("state_milestones") {
                    DashCard(title = "STATE ECONOMIC MILESTONES") {
                        TimelineEventChart(
                            events = listOf(
                                TimelineEvent(2015, "SMART CITY", true),
                                TimelineEvent(2018, "IND CORRIDOR", false),
                                TimelineEvent(2022, "5G DEPLOY", true),
                                TimelineEvent(2025, "GSDP $1T", false)
                            ),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            // 12. GSDP Trend Multi-line (C02)
            item {
                key("gsdp_trends") {
                    DashCard(title = "TOP 5 STATES GSDP (2000-25)") {
                        MultiLineChart(
                            data = listOf(
                                listOf(10f, 15f, 25f, 38f),
                                listOf(8f, 12f, 20f, 28f),
                                listOf(7f, 10f, 18f, 25f)
                            ),
                            colors = listOf(extendedColors.primary, extendedColors.accent, Color(0xFF00E676)),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 13. Industry Venn (C13)
            item {
                key("sector_overlap_venn") {
                    DashCard(title = "SECTORAL WORKFORCE OVERLAP") {
                        VennDiagramChart(modifier = Modifier.height(280.dp))
                    }
                }
            }

            // 14. Literacy Speedometer (C20)
            item {
                key("literacy_speedo") {
                    DashCard(title = "STATE LITERACY PERFORMANCE") {
                        SpeedometerGauge(value = 82.4f, max = 100f, label = "PERCENT", modifier = Modifier.height(200.dp))
                    }
                }
            }

            // 15. State Growth Area (C07)
            item {
                key("growth_composition") {
                    DashCard(title = "GSDP GROWTH COMPOSITION (%)") {
                        StackedAreaChart(
                            data = listOf(
                                listOf(20f, 22f, 25f, 28f),
                                listOf(30f, 32f, 35f, 38f),
                                listOf(50f, 46f, 40f, 34f)
                            ),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.GREEN_TEAL, GradPalette.ORANGE_PINK),
                            modifier = Modifier.height(220.dp)
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
fun PreviewStateComparisonScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        StateComparisonScreen(onBack = {})
    }
}
