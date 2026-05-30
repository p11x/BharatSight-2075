package com.bharatsight2075.ui.visualization

import androidx.compose.runtime.Stable

@Stable
sealed class VisualizationData {
    @Stable
    data class TimeSeries(val points: List<Point>) : VisualizationData()
    @Stable
    data class Composition(val segments: List<Segment>) : VisualizationData()
    @Stable
    data class Network(val nodes: List<Node>, val edges: List<Edge>) : VisualizationData()
    @Stable
    data class Spatial(val coordinates: List<GeoPoint>) : VisualizationData()
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

@Stable
data class Node(
    val id: String,
    val label: String,
    val category: Int = 0,
    val value: Float = 1f
)

@Stable
data class Edge(
    val from: String,
    val to: String,
    val value: Float = 1f
)

@Stable
data class GeoPoint(val lat: Float, val lng: Float, val value: Float = 1f, val label: String = "")

enum class ChartType {
    HOLOGRAPHIC_LINE,
    RADIAL_PROGRESS,
    TREEMAP_CIRCUIT,
    RADAR_SPIDER,
    SCATTER_CONSTELLATION,
    ISOMETRIC_BAR,
    CERN_RADIAL_NODE,
    POLAR_COXCOMB,
    TECH_SPECS_PANEL,
    DOTTED_PARTICLE_MAP,
    STACKED_ISOMETRIC_BAR,
    NEON_SPARKLINE,
    NEON_MATRIX_RAIN,
    NEURAL_FLOW_CHART,
    ORBITING_PARTICLES,
    THERMAL_HEATMAP,
    CIRCUIT_FLOW_BARGRID,
    SCATTER_DENSITY_MAP
}