package com.bharatsight2075.ui.visualization

import androidx.compose.runtime.Stable

@Stable
sealed class VisualizationData {
    @Stable
    data class TimeSeries(val points: List<Point>) : VisualizationData()
    @Stable
    data class Composition(val segments: List<Segment>) : VisualizationData()
}

@Stable
data class Point(val x: Float, val y: Float, val value: Float = 0f)

@Stable
data class Segment(
    val label: String, 
    val value: Float, 
    val maxValue: Float = 100f, 
    val color: androidx.compose.ui.graphics.Color? = null
)

enum class ChartType {
    AREA,
    MULTI_LINE,
    BAR,
    DONUT,
    GAUGE_HALF,
    GAUGE_SPEEDO,
    HEATMAP,
    CANDLE,
    SANKEY,
    BUBBLE,
    RADAR,
    WAVEFORM,
    STACKED_AREA,
    POLAR_AREA,
    TREEMAP,
    WATERFALL,
    TIMELINE,
    SCATTER_TREND,
    PROGRESS_HORIZONTAL,
    RING_CLUSTER,
    VENN,
    PARTICLE_FLOW,
    MIRROR_BAR,
    NUMBER_TICKER,
    MINI_SPARKLINE,
    CHOROPLETH_MAP,
    FORCE_GRAPH,
    LIQUID_GAUGE,
    BAR_RACE,
    BUBBLE_TREE,
    CANDLE_DEPTH,
    RIVER_MAP,
    SPIRAL_TIMELINE,
    HEX_MAP,
    RADAR_SCAN,
    CONE_CHART,
    RING_ORBITAL,
    WATERFALL_SPEC,
    BOND_GRAPH,
    HEAT_SPIRAL,
    GRAVITY_SCATTER,
    SANKEY_RIVER,
    TERRACE_FILL,
    FLOW_MATRIX
}
