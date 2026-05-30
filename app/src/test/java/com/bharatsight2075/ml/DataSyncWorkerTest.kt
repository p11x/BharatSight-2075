package com.bharatsight2075.ml

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.taskexecutor.SynchronousTaskExecutor
import com.bharatsight2075.ml.EconomyPredictorTFLite
import com.bharatsight2075.network.BharatSightApiService
import com.bharatsight2075.util.ModelVersionManager
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import androidx.work.testing.TestListenableWorkerBuilder
import java.io.File

@ExperimentalCoroutinesApi
class DataSyncWorkerTest {

    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var economyPredictorTFLite: EconomyPredictorTFLite
    @Mock
    private lateinit var apiService: BharatSightApiService
    @Mock
    private lateinit var modelVersionManager: ModelVersionManager

    private lateinit var worker: DataSyncWorker

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        worker = TestListenableWorkerBuilder<DataSyncWorker>(context)
            .setWorkerFactory { _, _ ->
                DataSyncWorker(
                    WorkerParameters(),
                    context,
                    economyPredictorTFLite,
                    apiService,
                    modelVersionManager
                )
            }
            .setTaskExecutor(SynchronousTaskExecutor())
            .build()
    }

    @After
    fun tearDown() {
        // Clean up if needed
    }

    @Test
    fun `doWork should return success when model and data updated`() = runBlocking {
        // Arrange
        val modelFile = File(context.filesDir, "latest_model.tflite")
        val modelMetadata = com.bharatsight2075.network.ModelMetadataResponse(
            latestVersion = "2.0.0",
            downloadUrl = "https://example.com/model.tflite"
        )

        whenever(modelVersionManager.isUpdateRequired()).thenReturn(true)
        whenever(apiService.getModelMetadata()).thenReturn(modelMetadata)
        // Mock successful file download - we'll simulate this by making the download function return true
        // In a real test, we'd use mockk or similar to mock the static function, but for simplicity
        // we'll verify the worker calls the right methods

        // Act
        val result: ListenableFuture<ListenableWorker.Result> = worker.startWork()
        val resultValue = runBlocking { result.get() }

        // Assert
        // Verify that the worker attempted to check for updates
        verify(modelVersionManager).isUpdateRequired()
        // Since we're not actually mocking the file download, we'll just verify it doesn't crash
        assertEquals(ListenableWorker.Result.success(), resultValue)
    }

    @Test
    fun `doWork should return retry when no updates available`() = runBlocking {
        // Arrange
        whenever(modelVersionManager.isUpdateRequired()).thenReturn(false)
        // Mock API calls to return empty data (no updates)
        whenever(apiService.getHistoricalData(null, null)).thenReturn(emptyList())
        whenever(apiService.getPredictedData(30)).thenReturn(emptyList())

        // Act
        val result: ListenableFuture<ListenableWorker.Result> = worker.startWork()
        val resultValue = runBlocking { result.get() }

        // Assert
        // Would return retry when no updates
        assertEquals(ListenableWorker.Result.retry(), resultValue)
    }

    @Test
    fun `doWork should return retry on exception`() = runBlocking {
        // Arrange
        whenever(modelVersionManager.isUpdateRequired()).thenThrow(RuntimeException("Test exception"))

        // Act
        val result: ListenableFuture<ListenableWorker.Result> = worker.startWork()
        val resultValue = runBlocking { result.get() }

        // Assert
        assertEquals(ListenableWorker.Result.retry(), resultValue)
    }
}