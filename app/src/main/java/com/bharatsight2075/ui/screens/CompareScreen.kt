package com.bharatsight2075.ui.screens

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

@Composable
fun CompareScreen(
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
            // 1. GDP Comparison Bar (C05)
            item {
                key("global_gdp_comp") {
                    DashCard(title = "WORLD GDP COMPARISON (\$T)") {
                        GradientBarChart(
                            data = listOf(3.7f, 27.4f, 17.7f, 4.4f, 4.2f, 3.1f),
                            labels = listOf("IND", "USA", "CHN", "DEU", "JPN", "GBR"),
                            modifier = Modifier.height(220.dp),
                            brush = GradPalette.TEAL_PURPLE
                        )
                    }
                }
            }

            // 2. Growth vs Inflation Scatter (C17)
            item {
                key("global_growth_scatter") {
                    DashCard(title = "GROWTH vs INFLATION (G6)") {
                        ScatterWithTrendLine(
                            data = listOf(
                                androidx.compose.ui.geometry.Offset(8.1f, 4.2f),
                                androidx.compose.ui.geometry.Offset(2.5f, 3.1f),
                                androidx.compose.ui.geometry.Offset(5.2f, 0.2f),
                                androidx.compose.ui.geometry.Offset(0.2f, 2.9f),
                                androidx.compose.ui.geometry.Offset(-0.1f, 3.2f),
                                androidx.compose.ui.geometry.Offset(0.3f, 4.0f)
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 3. Multi-Metric Radar (C09)
            item {
                key("multi_economy_radar") {
                    DashCard(title = "GLOBAL MACRO PERFORMANCE") {
                        RadarPolygonChart(
                            data = listOf(0.9f, 0.8f, 0.7f, 0.6f, 0.5f),
                            labels = emptyList(),
                            modifier = Modifier.height(300.dp),
                            brush = GradPalette.ORANGE_PINK
                        )
                    }
                }
            }

            // 4. Inflation vs Growth Mirror (C22)
            item {
                key("inflation_mirror_global") {
                    DashCard(title = "GROWTH vs INFLATION MIRROR") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(8.1f, 4.2f, "IND"),
                                MirrorData(2.5f, 3.1f, "USA"),
                                MirrorData(5.2f, 0.2f, "CHN")
                            ),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 5. HDI Ring Cluster (C12)
            item {
                key("hdi_rings") {
                    DashCard(title = "HDI PEER CONCENTRIC RINGS") {
                        RingProgressCluster(
                            rings = listOf(
                                RingData(0.64f, "IND", GradPalette.TEAL_PURPLE),
                                RingData(0.92f, "USA", GradPalette.ORANGE_PINK),
                                RingData(0.78f, "CHN", GradPalette.GREEN_TEAL)
                            ),
                            centerStat = "0.7",
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 6. FX Reserve Polar (C18)
            item {
                key("fx_polar_global") {
                    DashCard(title = "FX RESERVE COMPOSITION") {
                        PolarAreaChart(
                            data = listOf(620f, 3000f, 3200f, 450f, 1200f),
                            brushes = listOf(GradPalette.GOLD_WHITE, GradPalette.TEAL_PURPLE, GradPalette.GREEN_TEAL, GradPalette.ORANGE_PINK, GradPalette.YELLOW_ORANGE),
                            modifier = Modifier.height(260.dp)
                        )
                    }
                }
            }

            // 7. Market Cap Treemap (C16)
            item {
                key("mkt_cap_treemap") {
                    DashCard(title = "STOCK EXCHANGE MARKET CAP") {
                        TreemapChart(
                            weights = listOf(50.0, 45.0, 12.0, 8.0, 5.0),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.GREEN_TEAL, GradPalette.ORANGE_PINK),
                            modifier = Modifier.height(300.dp)
                        )
                    }
                }
            }

            // 8. Competitiveness Speedo (C20) + Waterfall (C19)
            item {
                key("global_gauges") {
                    TwoColumnRow {
                        DashCard(title = "COMPETITORS", modifier = Modifier.weight(1f)) {
                            SpeedometerGauge(value = 65.4f, max = 100f, label = "RANK INDEX", modifier = Modifier.height(180.dp))
                        }
                        DashCard(title = "RECOVERY Δ", modifier = Modifier.weight(1f)) {
                            WaterfallBarChart(
                                data = listOf(20f, 15f, 18f, 5f, 8f),
                                modifier = Modifier.height(180.dp)
                            )
                        }
                    }
                }
            }

            // 9. Export Venn (C13)
            item {
                key("export_venn_global") {
                    DashCard(title = "PRODUCT CATEGORY OVERLAP") {
                        VennDiagramChart(modifier = Modifier.height(280.dp))
                    }
                }
            }

            // 10. G20 Timeline (C23)
            item {
                key("g20_timeline") {
                    DashCard(title = "G20 ECONOMIC SUMMIT MILSTONES") {
                        TimelineEventChart(
                            events = listOf(
                                TimelineEvent(2023, "IND PRESIDENCY", true),
                                TimelineEvent(2024, "BRAZIL", false),
                                TimelineEvent(2025, "SOUTH AFRICA", true)
                            ),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            // 11. Global Sentiment Wave (C11)
            item {
                key("global_sentiment") {
                    DashCard(title = "MARKET SENTIMENT COHERENCE") {
                        WaveformChart(
                            modifier = Modifier.height(150.dp),
                            brush = GradPalette.CYAN_WHITE
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
fun PreviewCompareScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        CompareScreen(onBack = {})
    }
}
