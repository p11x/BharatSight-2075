package com.bharatsight2075.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.*
import com.bharatsight2075.ui.visualization.charts.*
import com.bharatsight2075.viewmodel.ChartDetailViewModel
import java.util.Locale

@Composable
fun ChartDetailScreen(
    navController: NavController,
    viewModel: ChartDetailViewModel = hiltViewModel()
) {
    val chartId = viewModel.chartId
    val chartMeta by viewModel.chartMeta.collectAsStateWithLifecycle()
    val chartData by viewModel.chartData.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    
    val extendedColors = SciFiTheme.extendedColors

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = chartMeta?.title ?: "VISUALIZATION",
                    badge = chartMeta?.badge,
                    badgeColor = chartMeta?.badgeColor ?: Color(0xFF00F5FF),
                    onBackClick = { navController.popBackStack() }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment=Alignment.Center) {
                    Column(horizontalAlignment=Alignment.CenterHorizontally,
                           verticalArrangement=Arrangement.spacedBy(12.dp)) {
                        CircularProgressIndicator(color=Color(0xFF00F5FF), strokeWidth=2.dp,
                            modifier=Modifier.size(40.dp))
                        Text("Loading ${chartMeta?.title ?: "visualization"}...",
                            fontSize=11.sp, fontFamily=FontFamily.Monospace, color=Color(0xFF00F5FF).copy(0.5f))
                    }
                }
            } else {
                // Safety: if data still empty after load, inject mock
                if (chartData.isEmpty()) {
                    LaunchedEffect(Unit) {
                        viewModel.injectMockData()
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // BLOCK A: Enlarged Chart
                    item {
                        val enlargedHeight = (LocalConfiguration.current.screenHeightDp * 0.42f).dp
                        var scale by remember { mutableFloatStateOf(1f) }
                        var offset by remember { mutableStateOf(Offset.Zero) }
                        val transformState = rememberTransformableState { zoom, pan, _ ->
                            scale = (scale * zoom).coerceIn(0.5f, 5f)
                            offset += pan
                        }

                        DashCard(chartId="${chartId}_enlarged", navController, chartMeta?.title ?: chartId) {
                            Box(Modifier
                                .fillMaxWidth().height(enlargedHeight)
                                .transformable(transformState)
                                .graphicsLayer { scaleX=scale; scaleY=scale; translationX=offset.x; translationY=offset.y }
                                .clip(RoundedCornerShape(12.dp))
                            ) {
                                ChartRenderer(chartMeta?.chartType ?: ChartType.AREA, chartData, Modifier.fillMaxSize())
                            }
                            // Reset zoom button
                            if (scale != 1f || offset != Offset.Zero) {
                                Box(Modifier.align(Alignment.TopEnd).padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp)).background(Color(0xFF00F5FF).copy(0.15f))
                                    .border(1.dp, Color(0xFF00F5FF).copy(0.3f), RoundedCornerShape(8.dp))
                                    .clickable { scale=1f; offset=Offset.Zero }.padding(6.dp)) {
                                    Icon(Icons.Outlined.CenterFocusStrong, null, Modifier.size(16.dp), Color(0xFF00F5FF))
                                }
                            }
                        }
                    }

                    // BLOCK B — Metadata row in a DashCard
                    item {
                        DashCard(
                            chartId = "meta_info_${chartId}",
                            navController = navController,
                            title = "Telemetry Data"
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                MetaChip(Icons.Outlined.Source, chartMeta?.dataSource ?: "RBI / NSE", Modifier.weight(1f))
                                MetaChip(Icons.Outlined.History, "2 min ago", Modifier.weight(1f))
                                MetaChip(Icons.Outlined.Analytics, "${chartData.size} points", Modifier.weight(1f))
                            }
                        }
                    }

                    // BLOCK C — Key Insights section
                    item {
                        DashCard(
                            chartId = "insights_${chartId}",
                            navController = navController,
                            title = "Key Insights"
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                chartMeta?.insights?.take(3)?.forEach { insight ->
                                    InsightRow(insight)
                                }
                            }
                        }
                    }

                    // BLOCK D — Raw data table
                    item {
                        DashCard(
                            chartId = "raw_data_${chartId}",
                            navController = navController,
                            title = "Raw Data"
                        ) {
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
                                    DataRow(index, data)
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

                    // BLOCK E — Related charts LazyRow
                    item {
                        Column {
                            Text(
                                "RELATED VISUALIZATIONS",
                                style = SciFiTheme.typography.SectionHead,
                                color = Color.White.copy(alpha = 0.5f),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            val related = ChartRegistry.getRelated(chartId)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(related.take(3)) { relatedMeta ->
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
                ChartRenderer(meta.chartType, ChartMockData.generateMockFor(meta.chartType), Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun ChartRenderer(type: ChartType, data: List<Float>, modifier: Modifier = Modifier) {
    when (type) {
        ChartType.AREA      -> GradientAreaChart(data=data, modifier=modifier, colors=GradPalette.TEAL_PURPLE)
        ChartType.BAR       -> GradientBarChart(data=data, modifier=modifier, colors=GradPalette.GREEN_TEAL)
        ChartType.LINE      -> MultiLineChart(datasets=listOf(data, data.map{it*0.85f}), modifier=modifier)
        ChartType.DONUT     -> GradientDonutChart(values=data.take(6), modifier=modifier)
        ChartType.GAUGE     -> SpeedometerGauge(value=(data.firstOrNull()?:50f)/100f, modifier=modifier)
        ChartType.HALF_DONUT-> HalfDonutGauge(value=(data.firstOrNull()?:50f)/100f, modifier=modifier)
        ChartType.RADAR     -> RadarPolygonChart(values=data.take(8).map{it/100f}, modifier=modifier)
        ChartType.HEATMAP   -> HeatmapGridChart(matrix=data.chunked(6).map{row->row.map{it/100f}}, modifier=modifier)
        ChartType.CANDLE    -> CandlestickChart(ohlcData=data.chunked(4).map{g-> listOf(g.getOrElse(0){50f},g.getOrElse(1){60f},g.getOrElse(2){40f},g.getOrElse(3){55f})}, modifier=modifier)
        ChartType.SANKEY    -> SankeyFlowChart(data=data, modifier=modifier)
        ChartType.BUBBLE    -> BubbleScatterChart(points=data.chunked(3).map{Triple(it.getOrElse(0){0.5f},it.getOrElse(1){0.5f},it.getOrElse(2){0.3f})}, modifier=modifier)
        ChartType.RING      -> RingProgressCluster(values=data.take(3).map{it/100f}, modifier=modifier)
        ChartType.WAVE      -> WaveformChart(data=data, modifier=modifier)
        ChartType.WATERFALL -> WaterfallBarChart(data=data, modifier=modifier)
        ChartType.POLAR     -> PolarAreaChart(values=data.take(8), modifier=modifier)
        ChartType.TREEMAP   -> TreemapChart(data=data, modifier=modifier)
        ChartType.TIMELINE  -> TimelineEventChart(events=data.take(8).mapIndexed{i,v->TimelineEvent("Event ${i+1}","${2020+i*2}",v)}, modifier=modifier)
        ChartType.LIQUID    -> LiquidFillGauge(fillPercent=(data.firstOrNull()?:65f)/100f, modifier=modifier)
        ChartType.STACKED   -> StackedAreaChart(datasets=listOf(data, data.map{it*0.6f}, data.map{it*0.3f}), modifier=modifier)
        else -> GradientAreaChart(data=data, modifier=modifier, colors=GradPalette.TEAL_PURPLE)
    }
}
