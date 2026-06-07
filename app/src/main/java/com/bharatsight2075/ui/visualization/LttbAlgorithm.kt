package com.bharatsight2075.ui.visualization

import kotlin.math.abs

/**
 * Largest-Triangle-Three-Buckets (LTTB) algorithm for downsampling data series.
 */
fun lttb(data: List<Float>, threshold: Int): List<Float> {
    if (data.size <= threshold || threshold <= 2) return data
    
    val result = mutableListOf<Float>()
    result.add(data.first())
    
    val bucketSize = (data.size - 2).toDouble() / (threshold - 2)
    var a = 0
    
    for (i in 0 until threshold - 2) {
        // Calculate point average for next bucket (target bucket)
        val avgRangeStart = (abs((i + 1) * bucketSize) + 1).toInt().coerceAtMost(data.size - 1)
        val avgRangeEnd = (abs((i + 2) * bucketSize) + 1).toInt().coerceAtMost(data.size)
        
        var avgY = 0f
        var avgCount = 0
        for (idx in avgRangeStart until avgRangeEnd) {
            avgY += data[idx]
            avgCount++
        }
        if (avgCount > 0) avgY /= avgCount
        
        // Find maximum area triangle between 'a', 'avg' and points in current bucket
        val rangeStart = (abs(i * bucketSize) + 1).toInt().coerceAtMost(data.size - 1)
        val rangeEnd = (abs((i + 1) * bucketSize) + 1).toInt().coerceAtMost(data.size)
        
        val aX = a.toFloat()
        val aY = data[a]
        
        var maxArea = -1f
        var nextA = rangeStart
        
        for (j in rangeStart until rangeEnd) {
            // Triangle area formula: 0.5 * abs(x1(y2 - y3) + x2(y3 - y1) + x3(y1 - y2))
            // Simplified here as we only need relative area comparison
            val area = abs((aX - (avgRangeStart + avgRangeEnd) / 2f) * (data[j] - aY) - 
                          (aX - j) * (avgY - aY))
            
            if (area > maxArea) {
                maxArea = area
                nextA = j
            }
        }
        
        result.add(data[nextA])
        a = nextA
    }
    
    result.add(data.last())
    return result
}
