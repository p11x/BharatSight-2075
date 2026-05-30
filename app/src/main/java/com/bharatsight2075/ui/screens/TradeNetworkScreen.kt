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
import com.bharatsight2075.ui.visualization.charts.SankeyFlow
import com.bharatsight2075.ui.visualization.charts.SankeyNode
import com.bharatsight2075.ui.visualization.charts.SankeyFlowChart
import com.bharatsight2075.ui.visualization.charts.MirrorData
import com.bharatsight2075.ui.visualization.charts.TimelineEvent

@Composable
fun TradeNetworkScreen(
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
            // 1. Force-Directed Graph (C21)
            item {
                key("network_flow") {
                    DashCard(title = "TRADE NETWORK PARTICLE DYNAMICS") {
                        ParticleFlowCanvas(
                            dataSize = 25,
                            modifier = Modifier.height(380.dp)
                        )
                    }
                }
            }

            // 2. Trade Volume Bars (C05)
            item {
                key("partner_ranking") {
                    DashCard(title = "TOP TRADING PARTNERS (VOL)") {
                        GradientBarChart(
                            data = listOf(128f, 118f, 85f, 52f, 49f, 35f, 30f, 22f, 21f, 20f),
                            labels = emptyList(),
                            modifier = Modifier.height(220.dp),
                            brush = GradPalette.YELLOW_ORANGE
                        )
                    }
                }
            }

            // 3. Export-Import Mirror (C22)
            item {
                key("trade_balance_mirror") {
                    DashCard(title = "EXPORT vs IMPORT VOLUME ($)") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(80f, 48f, "USA"),
                                MirrorData(30f, 88f, "CHN"),
                                MirrorData(50f, 35f, "UAE"),
                                MirrorData(42f, 28f, "DEU")
                            ),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 4. Commodity Polar (C18)
            item {
                key("export_composition") {
                    DashCard(title = "COMMODITY EXPORT SHARE (%)") {
                        PolarAreaChart(
                            data = listOf(85f, 65f, 45f, 30f, 25f, 20f, 15f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE, GradPalette.PURPLE_BLUE),
                            modifier = Modifier.height(260.dp)
                        )
                    }
                }
            }

            // 5. Indicators Row (C20 / C11)
            item {
                key("trade_logistics") {
                    TwoColumnRow {
                        DashCard(title = "LOGISTICS INDEX", modifier = Modifier.weight(1f)) {
                            SpeedometerGauge(value = 4.2f, max = 5f, label = "LPI SCORE", modifier = Modifier.height(180.dp))
                        }
                        DashCard(title = "PORT TRAFFIC", modifier = Modifier.weight(1f)) {
                            WaveformChart(
                                modifier = Modifier.height(180.dp),
                                brush = GradPalette.GREEN_TEAL
                            )
                        }
                    }
                }
            }

            // 6. Trade Route Sankey (C14)
            item {
                key("route_sankey") {
                    DashCard(title = "TRADE CORRIDOR FLOWS") {
                        SankeyFlowChart(
                            nodes = listOf(
                                SankeyNode("INDIA", 200f, Color(0xFF00F5FF)),
                                SankeyNode("EUROPE", 80f, Color(0xFFFF6B35)),
                                SankeyNode("ASEAN", 70f, Color(0xFF39FF14)),
                                SankeyNode("AFRICA", 50f, Color(0xFFFFD600))
                            ),
                            flows = listOf(
                                SankeyFlow(0, 1, 80f),
                                SankeyFlow(0, 2, 70f),
                                SankeyFlow(0, 3, 50f)
                            ),
                            modifier = Modifier.height(300.dp)
                        )
                    }
                }
            }

            // 7. Balance Waterfall (C19)
            item {
                key("trade_surplus_waterfall") {
                    DashCard(title = "CUMULATIVE TRADE SURPLUS/DEFICIT") {
                        WaterfallBarChart(
                            data = listOf(40f, 20f, -60f, -30f, 15f),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 8. Forex Earnings Area (C01)
            item {
                key("forex_earnings") {
                    DashCard(title = "FOREX EARNINGS FROM TRADE") {
                        GradientAreaChart(
                            data = listOf(45f, 52f, 48f, 60f, 65f, 72f),
                            modifier = Modifier.height(200.dp),
                            brush = GradPalette.GREEN_TEAL,
                            strokeColor = Color(0xFF00E676)
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
fun PreviewTradeNetworkScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        TradeNetworkScreen(onBack = {})
    }
}
