package com.bharatsight2075.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.*
import com.bharatsight2075.ui.visualization.charts.*
import com.bharatsight2075.viewmodel.ChartDetailViewModel
import java.util.Locale

@Composable
fun ChartDetailScreen(
    navController: NavController,
    chartId: String,
    viewModel: ChartDetailViewModel = hiltViewModel()
) {
    val meta = viewModel.chartMeta ?: return
    val chartData by viewModel.chartData.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val extendedColors = SciFiTheme.extendedColors
    
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformableState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = meta.title,
                    badge = meta.badge,
                    badgeColor = meta.badgeColor,
                    onBackClick = { navController.popBackStack() }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // BLOCK 1 — Enlarged Chart
            item {
                DashCard(
                    chartId = "detail_${meta.chartId}",
                    navController = navController,
                    title = "Interactive Viewport"
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 0.45f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black.copy(alpha = 0.3f))
                            .transformable(state = transformableState),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier.graphicsLayer {
                                scaleX = scale.coerceIn(0.5f, 3f)
                                scaleY = scale.coerceIn(0.5f, 3f)
                                translationX = offset.x
                                translationY = offset.y
                            }
                        ) {
                            ChartRenderer(meta.chartType, chartData, Modifier.fillMaxSize())
                        }
                    }
                }
            }

            // BLOCK 2 — Metadata Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetaChip(Icons.Outlined.Source, meta.dataSource, Modifier.weight(1f))
                    MetaChip(Icons.Outlined.History, "2 min ago", Modifier.weight(1f))
                    MetaChip(Icons.Outlined.Analytics, "${chartData.size} points", Modifier.weight(1f))
                }
            }

            // BLOCK 3 — Key Insights
            item {
                DashCard(chartId = "insights_${meta.chartId}", navController = navController, title = "Key Insights") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        meta.insights.forEach { insight ->
                            InsightRow(insight)
                        }
                    }
                }
            }

            // BLOCK 4 — Data Table
            item {
                DashCard(chartId = "raw_data_${meta.chartId}", navController = navController, title = "Raw Data") {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("INDEX", style = SciFiTheme.typography.ChartCaption, color = Color.White.copy(alpha = 0.5f))
                            Text("VALUE", style = SciFiTheme.typography.ChartCaption, color = Color.White.copy(alpha = 0.5f))
                            Text("CHG%", style = SciFiTheme.typography.ChartCaption, color = Color.White.copy(alpha = 0.5f))
                        }
                        
                        chartData.take(10).forEachIndexed { index, data ->
                            val value = when(data) {
                                is Float -> data
                                is CandleData -> data.close
                                else -> 0f
                            }
                            DataRow(index, value)
                        }
                        
                        if (chartData.size > 10) {
                            Text(
                                text = "+ ${chartData.size - 10} more records",
                                style = SciFiTheme.typography.ChartCaption,
                                color = extendedColors.primary.copy(alpha = 0.6f),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            // BLOCK 5 — Related Charts
            item {
                Column {
                    Text(
                        "RELATED VISUALIZATIONS",
                        style = SciFiTheme.typography.SectionHead,
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    val related = ChartRegistry.getRelated(meta.chartId)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(related) { relatedMeta ->
                            RelatedChartCard(relatedMeta) {
                                navController.navigate(Routes.CHART_DETAIL.replace("{chartId}", relatedMeta.chartId))
                            }
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun MetaChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(48.dp),
        color = Color(0xFF0E0E1A),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = SciFiTheme.extendedColors.primary, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(6.dp))
            Text(text.uppercase(), fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f), style = SciFiTheme.typography.BodyMono)
        }
    }
}

@Composable
fun InsightRow(text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(6.dp)
                .background(SciFiTheme.extendedColors.primary, CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.85f))
    }
}

@Composable
fun DataRow(index: Int, value: Float) {
    val bgColor = if (index % 2 == 0) Color.Transparent else Color.White.copy(alpha = 0.05f)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("#$index", style = SciFiTheme.typography.BodyMono, fontSize = 11.sp, color = Color.White.copy(alpha = 0.5f))
        Text(String.format(Locale.getDefault(), "%.2f", value), style = SciFiTheme.typography.BodyMono, fontSize = 11.sp, color = Color.White)
        Text("▲ 0.2%", style = SciFiTheme.typography.BodyMono, fontSize = 11.sp, color = Color(0xFF00E676))
    }
}

@Composable
fun RelatedChartCard(meta: ChartMeta, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(140.dp, 100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0E0E1A))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column {
            Text(meta.title.uppercase(), fontSize = 8.sp, color = Color.White.copy(alpha = 0.6f), maxLines = 1)
            Spacer(Modifier.height(8.dp))
            Box(Modifier.fillMaxSize().alpha(0.5f)) {
                ChartRenderer(meta.chartType, ChartMockData.generateMockData(meta.chartType), Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun ChartRenderer(type: ChartType, data: List<Any>, modifier: Modifier = Modifier) {
    when(type) {
        ChartType.AREA -> GradientAreaChart(data = data.filterIsInstance<Float>(), modifier = modifier)
        ChartType.BAR -> GradientBarChart(data = data.filterIsInstance<Float>(), labels = emptyList(), modifier = modifier)
        ChartType.NUMBER_TICKER -> GlowingNumberTicker(value = 37f, unit = "T", delta = "8.1%", modifier = modifier)
        ChartType.DONUT -> GradientDonutChart(values = data.filterIsInstance<Float>(), brushes = emptyList(), label = "GVA", modifier = modifier)
        ChartType.MULTI_LINE -> MultiLineChart(data = listOf(data.filterIsInstance<Float>()), colors = emptyList(), modifier = modifier)
        ChartType.GAUGE_HALF -> HalfDonutGauge(value = (data.firstOrNull() as? Float) ?: 0.644f, max = 1f, label = "HDI", modifier = modifier)
        ChartType.GAUGE_SPEEDO -> SpeedometerGauge(value = (data.firstOrNull() as? Float) ?: 7.8f, max = 15f, label = "RATE", modifier = modifier)
        ChartType.HEATMAP -> HeatmapGridChart(data = (data as? List<List<Float>>) ?: emptyList(), modifier = modifier)
        ChartType.CANDLE -> CandlestickChart(data = data.filterIsInstance<CandleData>(), modifier = modifier)
        ChartType.SANKEY -> SankeyFlowChart(nodes = emptyList(), flows = emptyList(), modifier = modifier)
        ChartType.BUBBLE -> BubbleScatterChart(data = data.filterIsInstance<BubblePoint>(), modifier = modifier)
        ChartType.RADAR -> RadarPolygonChart(data = data.filterIsInstance<Float>(), labels = emptyList(), modifier = modifier)
        ChartType.WAVEFORM -> WaveformChart(modifier = modifier)
        ChartType.STACKED_AREA -> StackedAreaChart(data = (data as? List<List<Float>>) ?: emptyList(), brushes = emptyList(), modifier = modifier)
        ChartType.POLAR_AREA -> PolarAreaChart(data = data.filterIsInstance<Float>(), brushes = emptyList(), modifier = modifier)
        ChartType.TREEMAP -> TreemapChart(weights = data.filterIsInstance<Double>(), brushes = emptyList(), modifier = modifier)
        ChartType.WATERFALL -> WaterfallBarChart(data = data.filterIsInstance<Float>(), modifier = modifier)
        ChartType.TIMELINE -> TimelineEventChart(events = data.filterIsInstance<TimelineEvent>(), modifier = modifier)
        ChartType.SCATTER_TREND -> ScatterWithTrendLine(data = data.filterIsInstance<Offset>(), modifier = modifier)
        ChartType.PROGRESS_HORIZONTAL -> HorizontalProgressBars(items = data.filterIsInstance<Pair<String, Float>>(), modifier = modifier)
        ChartType.RING_CLUSTER -> RingProgressCluster(rings = data.filterIsInstance<RingData>(), modifier = modifier)
        ChartType.VENN -> VennDiagramChart(modifier = modifier)
        ChartType.PARTICLE_FLOW -> ParticleFlowCanvas(modifier = modifier)
        ChartType.MIRROR_BAR -> MirrorBarChart(data = data.filterIsInstance<MirrorData>(), modifier = modifier)
        ChartType.MINI_SPARKLINE -> MiniSparklineCard(label = "STAT", value = "100", data = data.filterIsInstance<Float>(), modifier = modifier)
    }
}
