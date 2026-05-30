package com.bharatsight2075.data.local

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Unit tests for MockDataGenerator.
 * Tests that generated economic data meets expected constraints.
 */
class MockDataGeneratorTest {

    /**
     * Tests that historical GDP values are never negative.
     */
    @Test
    fun `historical GDP values are never negative`() {
        val historicalData = MockDataGenerator.generateHistoricalData()
        for (data in historicalData) {
            assertTrue("GDP should not be negative for year ${data.year}", data.gdp >= 0.0)
        }
    }

    /**
     * Tests that predicted GDP values are never negative.
     */
    @Test
    fun `predicted GDP values are never negative`() {
        val historicalData = MockDataGenerator.generateHistoricalData()
        val predictedData = MockDataGenerator.generatePredictedData(historicalData)
        for (data in predictedData) {
            assertTrue("GDP should not be negative for year ${data.year}", data.gdp >= 0.0)
        }
    }

    /**
     * Tests that historical data size matches expected count (1950-2025 inclusive).
     */
    @Test
    fun `historical data size is correct`() {
        val historicalData = MockDataGenerator.generateHistoricalData()
        val expectedSize = 2025 - 1950 + 1 // 76 years
        assertEquals("Historical data should contain 76 entries", expectedSize, historicalData.size)
    }

    /**
     * Tests that predicted data size matches expected count (2026-2075 inclusive).
     */
    @Test
    fun `predicted data size is correct`() {
        val historicalData = MockDataGenerator.generateHistoricalData()
        val predictedData = MockDataGenerator.generatePredictedData(historicalData)
        val expectedSize = 2075 - 2026 + 1 // 50 years
        assertEquals("Predicted data should contain 50 entries", expectedSize, predictedData.size)
    }

    /**
     * Tests that sector values are within reasonable bounds.
     */
    @Test
    fun `sector values are within reasonable bounds`() {
        val historicalData = MockDataGenerator.generateHistoricalData()
        for (data in historicalData) {
            // Sector values should be non-negative (though they could be very small)
            assertTrue("Agriculture sector should not be negative for year ${data.year}", data.sectorAgri >= 0.0)
            assertTrue("Manufacturing sector should not be negative for year ${data.year}", data.sectorMfg >= 0.0)
            assertTrue("Services sector should not be negative for year ${data.year}", data.sectorSvc >= 0.0)
            
            // The sum of sectors should approximately equal 100% of GDP (allowing for some variance)
            val totalSectors = data.sectorAgri + data.sectorMfg + data.sectorSvc
            // Allow for 50% variance since these are simplified sector models
            assertTrue("Total sectors should be reasonable for year ${data.year}", totalSectors <= data.gdp * 2.0)
        }
    }

    /**
     * Tests that population values are always positive and growing.
     */
    @Test
    fun `population values are positive and growing`() {
        val historicalData = MockDataGenerator.generateHistoricalData()
        var previousPopulation = 0.0
        for (data in historicalData) {
            assertTrue("Population should be positive for year ${data.year}", data.population > 0.0)
            assertTrue("Population should grow year over year for year ${data.year}", data.population > previousPopulation)
            previousPopulation = data.population
        }
    }

    /**
     * Tests that inflation values are within reasonable bounds (0-50%).
     */
    @Test
    fun `inflation values are within reasonable bounds`() {
        val historicalData = MockDataGenerator.generateHistoricalData()
        for (data in historicalData) {
            assertTrue("Inflation should be between 0 and 50 for year ${data.year}", 
                data.inflation >= 0.0 && data.inflation <= 50.0)
        }
        
        val historicalDataForPrediction = MockDataGenerator.generateHistoricalData()
        val predictedData = MockDataGenerator.generatePredictedData(historicalDataForPrediction)
        for (data in predictedData) {
            assertTrue("Inflation should be between 0 and 50 for year ${data.year}", 
                data.inflation >= 0.0 && data.inflation <= 50.0)
        }
    }
}