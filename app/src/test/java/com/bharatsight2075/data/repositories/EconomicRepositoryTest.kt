package com.bharatsight2075.data.repositories

import com.bharatsight2075.data.local.EconomicDao
import com.bharatsight2075.data.local.HistoricalEconomicData
import com.bharatsight2075.data.local.PredictedEconomicData
import com.bharatsight2075.network.BharatSightApiService
import com.bharatsight2075.network.HistoricalEconomicData as ApiHistoricalData
import com.bharatsight2075.network.ModelMetadataResponse
import com.bharatsight2075.network.PredictedEconomicData as ApiPredictedData
import com.bharatsight2075.util.ModelVersionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.ArrayList
import java.util.List

@ExperimentalCoroutinesApi
class EconomicRepositoryTest {

    @Mock
    private lateinit var dao: EconomicDao
    @Mock
    private lateinit var apiService: BharatSightApiService
    @Mock
    private lateinit var modelVersionManager: ModelVersionManager

    private lateinit var repository: EconomicRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = EconomicRepository(dao, apiService, modelVersionManager)
    }

    @After
    fun tearDown() {
        resetMain()
    }

    @Test
    fun `syncEconomyData should fetch and convert data correctly`() = runBlocking {
        // Arrange
        val apiHistoricalData = ApiHistoricalData(
            timestamp = 1640995200000L, // 2022-01-01
            gdpGrowth = 6.5,
            inflationRate = 4.2
        )
        val apiPredictedData = ApiPredictedData(
            timestamp = 1643673600000L, // 2022-02-01
            predictedGdpGrowth = 6.8
        )

        val apiHistoricalList = listOf(apiHistoricalData)
        val apiPredictedList = listOf(apiPredictedData)

        whenever(apiService.getHistoricalData(null, null)).thenReturn(apiHistoricalList)
        whenever(apiService.getPredictedData(30)).thenReturn(apiPredictedList)
        whenever(modelVersionManager.isUpdateRequired()).thenReturn(false)

        // Act
        val result = repository.syncEconomyData()

        // Assert
        assertEquals(true, result)

        // Verify data conversion and insertion
        val historicalCaptor = ArgumentCaptor.forClass(List::class.java)
        val predictedCaptor = ArgumentCaptor.forClass(List::class.java)
        verify(dao).clearHistoricalData()
        verify(dao).clearPredictedData()
        verify(dao).insertHistoricalData(historicalCaptor.capture())
        verify(dao).insertPredictedData(predictedCaptor.capture())

        val insertedHistorical = historicalCaptor.value
        val insertedPredicted = predictedCaptor.value

        assertEquals(1, insertedHistorical.size)
        assertEquals(1, insertedPredicted.size)

        // Check historical data conversion
        val historical = insertedHistorical[0]
        assertEquals(2022, historical.year)
        assertEquals(6.5, historical.gdp, 0.001)
        assertEquals(4.2, historical.inflation, 0.001)

        // Check predicted data conversion
        val predicted = insertedPredicted[0]
        assertEquals(2022, predicted.year)
        assertEquals(6.8, predicted.gdp, 0.001)
    }

    @Test
    fun `syncEconomyData should handle model update check`() = runBlocking {
        // Arrange
        val modelMetadata = ModelMetadataResponse(
            latestVersion = "2.0.0",
            downloadUrl = "https://example.com/model.tflite"
        )
        val apiHistoricalList = listOf<ApiHistoricalData>()
        val apiPredictedList = listOf<ApiPredictedData>()

        whenever(apiService.getModelMetadata()).thenReturn(modelMetadata)
        whenever(apiService.getHistoricalData(null, null)).thenReturn(apiHistoricalList)
        whenever(apiService.getPredictedData(30)).thenReturn(apiPredictedList)
        whenever(modelVersionManager.isUpdateRequired()).thenReturn(true)

        // Act
        val result = repository.syncEconomyData()

        // Assert
        assertEquals(true, result)
        verify(modelVersionManager).isUpdateRequired()
        verify(apiService).getModelMetadata()
        verify(modelVersionManager).storeDownloadUrl(modelMetadata.downloadUrl)
        // Note: Actual version marking happens in worker after successful download
    }

    @Test
    fun `syncEconomyData should return false on exception`() = runBlocking {
        // Arrange
        whenever(apiService.getHistoricalData(null, null)).thenThrow(RuntimeException("Network error"))
        whenever(modelVersionManager.isUpdateRequired()).thenReturn(false)

        // Act
        val result = repository.syncEconomyData()

        // Assert
        assertEquals(false, result)
    }
}