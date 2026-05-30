package com.bharatsight2075.ui.visualization.treemap

import androidx.compose.ui.geometry.Rect

data class TreemapNode(val weight: Double, val originalIndex: Int)
data class TreemapRect(val rect: Rect, val originalIndex: Int)

/**
 * Implementation of the Squarified Treemap algorithm (Bruls et al.)
 * Optimized for Jetpack Compose coordinate systems.
 */
object TreemapLayoutEngine {
    fun squarify(weights: List<Double>, container: Rect): List<TreemapRect> {
        if (weights.isEmpty()) return emptyList()
        val totalWeight = weights.sum()
        val nodes = weights.mapIndexed { index, w -> TreemapNode(w, index) }
            .sortedByDescending { it.weight }
        
        val result = mutableListOf<TreemapRect>()
        squarifyRecursive(nodes, mutableListOf(), container, totalWeight, result)
        return result
    }

    private fun squarifyRecursive(
        nodes: List<TreemapNode>,
        row: MutableList<TreemapNode>,
        container: Rect,
        totalWeight: Double,
        result: MutableList<TreemapRect>
    ) {
        if (nodes.isEmpty()) {
            if (row.isNotEmpty()) layoutRow(row, container, totalWeight, result)
            return
        }

        val nextNode = nodes.first()
        val currentWorst = if (row.isEmpty()) Double.MAX_VALUE else worst(row, container, totalWeight)
        val nextWorst = worst(row + nextNode, container, totalWeight)

        if (nextWorst <= currentWorst) {
            squarifyRecursive(nodes.drop(1), (row + nextNode).toMutableList(), container, totalWeight, result)
        } else {
            val newContainer = layoutRow(row, container, totalWeight, result)
            squarifyRecursive(nodes, mutableListOf(), newContainer, totalWeight - row.sumOf { it.weight }, result)
        }
    }

    private fun worst(row: List<TreemapNode>, container: Rect, totalWeight: Double): Double {
        val side = minOf(container.width, container.height).toDouble()
        if (side == 0.0) return Double.MAX_VALUE
        
        val rowWeight = row.sumOf { it.weight }
        val minWeight = row.minOf { it.weight }
        val maxWeight = row.maxOf { it.weight }
        
        val sSq = side * side
        val wSq = (rowWeight / totalWeight) * (rowWeight / totalWeight)
        
        val scale = totalWeight / (container.width * container.height)
        val rowArea = rowWeight / scale
        val sSide = minOf(container.width, container.height).toDouble()
        
        return maxOf(
            (sSide * sSide * maxWeight / scale) / (rowArea * rowArea),
            (rowArea * rowArea) / (sSide * sSide * minWeight / scale)
        )
    }

    private fun layoutRow(
        row: List<TreemapNode>, 
        container: Rect, 
        totalWeight: Double, 
        result: MutableList<TreemapRect>
    ): Rect {
        val horizontal = container.width >= container.height
        val totalRowWeight = row.sumOf { it.weight }
        val containerArea = container.width * container.height
        val rowArea = (totalRowWeight / totalWeight) * containerArea
        
        val thickness = if (horizontal) (rowArea / container.height) else (rowArea / container.width)
        
        var currentOffset = if (horizontal) container.top else container.left
        row.forEach { node ->
            val nodeArea = (node.weight / totalWeight) * containerArea
            val breadth = nodeArea / thickness
            
            val rect = if (horizontal) {
                Rect(container.left, currentOffset, container.left + thickness.toFloat(), currentOffset + breadth.toFloat())
            } else {
                Rect(currentOffset, container.top, currentOffset + breadth.toFloat(), container.top + thickness.toFloat())
            }
            
            result.add(TreemapRect(rect, node.originalIndex))
            currentOffset += breadth.toFloat()
        }

        return if (horizontal) {
            Rect(container.left + thickness.toFloat(), container.top, container.right, container.bottom)
        } else {
            Rect(container.left, container.top + thickness.toFloat(), container.right, container.bottom)
        }
    }
}
