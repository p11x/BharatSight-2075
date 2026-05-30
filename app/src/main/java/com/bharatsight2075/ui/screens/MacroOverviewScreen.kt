package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.DashCard
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.components.TwoColumnRow
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun MacroOverviewScreen(
    marketData: List<com.bharatsight2075.service.LiveMarketData> = emptyList(),
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
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
            // 1. GDP Hero Panel (C24)
            item {
                key("gdp_hero") {
                    DashCard(title = "Core Economic Indicator") {
                        GlowingNumberTicker(
                            value = 37.00f,
                            unit = "USD TRILLION",
                            delta = "▲ 8.1% YoY"
                        )
                    }
                }
            }

            // 2. GDP 50-Year Trajectory (C01)
            item {
                key("gdp_traj") {
                    DashCard(title = "GDP 50-YEAR PROJECTION") {
                        GradientAreaChart(
                            data = listOf(10f, 15f, 25f, 35f, 50f, 80f, 120f, 180f, 250f, 370f),
                            modifier = Modifier.height(240.dp),
                            brush = GradPalette.TEAL_PURPLE,
                            strokeColor = extendedColors.primary
                        )
                    }
                }
            }

            // 3. Key Indicators Rings (C12) + HDI Gauge (C04)
            item {
                key("indicator_rings") {
                    TwoColumnRow {
                        DashCard(title = "STABILITY RINGS", modifier = Modifier.weight(1f)) {
                            RingProgressCluster(
                                rings = listOf(
                                    RingData(0.81f, "GDP", GradPalette.TEAL_PURPLE),
                                    RingData(0.65f, "INF", GradPalette.ORANGE_PINK),
                                    RingData(0.42f, "TRD", GradPalette.GREEN_TEAL)
                                ),
                                centerStat = "81%",
                                modifier = Modifier.height(180.dp)
                            )
                        }
                        DashCard(title = "HDI GAUGE", modifier = Modifier.weight(1f)) {
                            HalfDonutGauge(
                                value = 0.644f,
                                max = 1.0f,
                                label = "HDI INDEX",
                                modifier = Modifier.height(180.dp)
                            )
                        }
                    }
                }
            }

            // 4. Sector Composition (C03)
            item {
                key("sector_donut") {
                    DashCard(title = "SECTOR COMPOSITION") {
                        GradientDonutChart(
                            values = listOf(40f, 30f, 30f),
                            brushes = listOf(GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.PURPLE_BLUE),
                            label = "GVA SHARE",
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 5. Historical Growth (C05)
            item {
                key("growth_bars") {
                    DashCard(title = "YOY GROWTH (2015-25)") {
                        GradientBarChart(
                            data = listOf(6.5f, 8.2f, 7.1f, 4.2f, 3.8f, 7.5f, 9.1f, 8.4f, 7.9f, 8.1f),
                            labels = emptyList(),
                            modifier = Modifier.height(200.dp),
                            brush = GradPalette.GREEN_TEAL
                        )
                    }
                }
            }

            // 6. Trade Balance Mirror (C22)
            item {
                key("trade_mirror") {
                    DashCard(title = "TRADE BALANCE (MONTHLY)") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(80f, 65f, "JAN"),
                                MirrorData(75f, 70f, "FEB"),
                                MirrorData(90f, 85f, "MAR"),
                                MirrorData(85f, 95f, "APR"),
                                MirrorData(110f, 100f, "MAY")
                            ),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 7. Inflation Trends (C02)
            item {
                key("inflation_line") {
                    DashCard(title = "INFLATION VARIANCE (CPI/WPI)") {
                        MultiLineChart(
                            data = listOf(
                                listOf(4.2f, 4.5f, 4.1f, 3.9f, 4.0f, 4.2f),
                                listOf(3.5f, 3.8f, 3.2f, 3.0f, 3.4f, 3.5f)
                            ),
                            colors = listOf(extendedColors.accent, extendedColors.primary),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 8. Employment Speedometer (C20) + Credit Wave (C11)
            item {
                key("employment_speedo") {
                    TwoColumnRow {
                        DashCard(title = "UNEMPLOYMENT", modifier = Modifier.weight(1f)) {
                            SpeedometerGauge(
                                value = 7.8f,
                                max = 15f,
                                label = "RATE %",
                                modifier = Modifier.height(180.dp)
                            )
                        }
                        DashCard(title = "CREDIT WAVE", modifier = Modifier.weight(1f)) {
                            WaveformChart(
                                modifier = Modifier.height(180.dp),
                                brush = GradPalette.CYAN_WHITE
                            )
                        }
                    }
                }
            }

            // 9. Rate History Candles (C10)
            item {
                key("rate_candles") {
                    DashCard(title = "REPO RATE FLUCTUATION (OHLC)") {
                        CandlestickChart(
                            data = listOf(
                                CandleData(6.25f, 6.50f, 6.00f, 6.40f),
                                CandleData(6.40f, 6.75f, 6.25f, 6.50f),
                                CandleData(6.50f, 6.50f, 6.25f, 6.25f),
                                CandleData(6.25f, 6.75f, 6.10f, 6.50f)
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 10. Global Rank Heatmap (C15)
            item {
                key("rank_heatmap") {
                    DashCard(title = "GLOBAL MACRO RANKING (G20)") {
                        HeatmapGridChart(
                            data = List(6) { List(8) { (10..100).random() / 100f } },
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 11. Remittance Source Flow (C14)
            item {
                key("remit_sankey") {
                    DashCard(title = "REMITTANCE SOURCE FLOW") {
                        SankeyFlowChart(
                            nodes = listOf(
                                SankeyNode("USA", 100f, Color(0xFF00F5FF)),
                                SankeyNode("UAE", 80f, Color(0xFFFF6B35)),
                                SankeyNode("INDIA", 180f, Color(0xFF39FF14))
                            ),
                            flows = listOf(
                                SankeyFlow(0, 2, 90f),
                                SankeyFlow(1, 2, 70f)
                            ),
                            modifier = Modifier.height(280.dp)
                        )
                    }
                }
            }

            // 12. M3 Money Supply Stack (C07)
            item {
                key("money_supply_stack") {
                    DashCard(title = "M3 MONEY SUPPLY COMPONENTS") {
                        StackedAreaChart(
                            data = listOf(
                                listOf(30f, 32f, 35f, 38f, 40f),
                                listOf(20f, 22f, 25f, 24f, 28f),
                                listOf(15f, 18f, 16f, 20f, 22f)
                            ),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 13. Recovery Dynamics (C08)
            item {
                key("recovery_bubbles") {
                    DashCard(title = "SECTOR RECOVERY DYNAMICS") {
                        BubbleScatterChart(
                            data = listOf(
                                BubbleData(20f, 30f, 0.6f, "IT"),
                                BubbleData(50f, 40f, 1.2f, "FIN"),
                                BubbleData(80f, 10f, 0.4f, "AGRI"),
                                BubbleData(40f, 70f, 0.8f, "MFG")
                            ),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 14. Fiscal Target Progress (C06)
            item {
                key("fiscal_targets") {
                    DashCard(title = "FISCAL REFORM PROGRESS") {
                        HorizontalProgressBars(
                            items = listOf(
                                "DEFICIT CONSOLIDATION" to 0.75f,
                                "TAX COMPLIANCE" to 0.88f,
                                "DIGITAL RUPEE ROLLOUT" to 0.42f
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            // 15. Sectoral Overlap (C13)
            item {
                key("sector_overlap") {
                    DashCard(title = "SECTORAL SYNERGY MAP") {
                        VennDiagramChart(modifier = Modifier.height(280.dp))
                    }
                }
            }

            // 16. Macro Timeline (C23)
            item {
                key("macro_timeline") {
                    DashCard(title = "HISTORICAL MACRO MILESTONES") {
                        TimelineEventChart(
                            events = listOf(
                                TimelineEvent(2010, "BASE YEAR CHG", true),
                                TimelineEvent(2016, "GST LAUNCH", false),
                                TimelineEvent(2021, "PLI SCHEME", true),
                                TimelineEvent(2024, "IND #5 GDP", false)
                            ),
                            modifier = Modifier.height(180.dp)
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
fun PreviewMacroOverviewScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        MacroOverviewScreen(onBack = {})
    }
}
