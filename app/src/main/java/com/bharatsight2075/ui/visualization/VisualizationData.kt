package com.bharatsight2075.ui.visualization

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset

@Stable
sealed class VisualizationData {
    @Stable
    data class TimeSeries(val points: List<Point>, val label: String = "") : VisualizationData()
    @Stable
    data class Composition(val segments: List<Segment>) : VisualizationData()
    @Stable
    data class Network(
        val nodes: List<NetworkNode>,
        val edges: List<NetworkEdge>
    ) : VisualizationData()
    @Stable
    data class Spatial(
        val spatialPoints: List<Offset>,
        val values: List<Float>
    ) : VisualizationData()
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
data class NetworkNode(val id: String, val label: String, val value: Float)
@Stable
data class NetworkEdge(val fromId: String, val toId: String, val weight: Float)
