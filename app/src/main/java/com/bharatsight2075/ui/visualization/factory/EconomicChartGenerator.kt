package com.bharatsight2075.ui.visualization.factory

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.visualization.VisualizationData
import com.bharatsight2075.ui.visualization.VisualizationData.*
import com.bharatsight2075.ui.visualization.line.HolographicLineChart
import com.bharatsight2075.ui.visualization.bar.StackedIsometricBarChart
import com.bharatsight2075.ui.visualization.bar.StackedBarData
import com.bharatsight2075.ui.visualization.cluster.RadialProgressCluster
import com.bharatsight2075.ui.visualization.cluster.CernRadialNodeMap
import com.bharatsight2075.ui.visualization.map.DottedParticleWorldMap

@Composable
fun EconomicChartGenerator(
    data: VisualizationData,
    modifier: Modifier = Modifier,
    onChartClick: ((String) -> Unit)? = null
) {
    when (data) {
        is TimeSeries -> {
            HolographicLineChart(
                modifier = modifier.fillMaxWidth().height(220.dp),
                points = data.points.map { it.y }
            )
        }
        is Composition -> {
            if (data.segments.size >= 2) {
                StackedIsometricBarChart(
                    modifier = modifier.fillMaxWidth().height(250.dp),
                    data = listOf(
                        StackedBarData(
                            label = "CORE",
                            segments = data.segments.map { it.value to (it.color ?: androidx.compose.ui.graphics.Color.Gray) }
                        )
                    )
                )
            } else {
                RadialProgressCluster(
                    label = "COMPOSITION",
                    value = data.segments.firstOrNull()?.let { it.value / (it.maxValue.takeIf { v -> v > 0 } ?: 1f) } ?: 0f,
                    modifier = modifier.height(180.dp)
                )
            }
        }
        is Network -> {
            DottedParticleWorldMap(modifier = modifier)
        }
        is Spatial -> {
            CernRadialNodeMap(
                modifier = modifier,
                nodeCount = data.spatialPoints.size
            )
        }
    }
}
