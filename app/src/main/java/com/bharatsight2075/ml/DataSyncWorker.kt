package com.bharatsight2075.ml

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.bharatsight2075.data.local.EconomicConstants
import com.bharatsight2075.data.local.MockDataGenerator
import com.bharatsight2075.data.local.PolicyImpact
import com.bharatsight2075.data.repositories.EconomicRepository
import com.bharatsight2075.ml.EconomyPredictorTFLite
import com.bharatsight2075.network.BharatSightApiService
import com.bharatsight2075.util.ModelVersionManager
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val economyPredictorTFLite: EconomyPredictorTFLite,
    private val economicRepository: EconomicRepository,
    private val modelVersionManager: ModelVersionManager,
    private val apiService: BharatSightApiService
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "DataSyncWorker"
        private const val MODEL_DOWNLOAD_URL = "https://example.com/models/economy_2075_model.tflite"
        private const val HISTORICAL_DATA_URL = "https://example.com/data/historical_economic_data.json"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.i(TAG, "Starting data synchronization...")
            
            // Step 1: Check for model updates
            setProgress(workDataOf("progress" to 0, "status" to "Checking for model updates"))
            val modelUpdateRequired = modelVersionManager.isUpdateRequired()
            var modelUpdated = false
            
            if (modelUpdateRequired) {
                // Get model metadata
                setProgress(workDataOf("progress" to 10, "status" to "Fetching model metadata"))
                val modelMetadata = apiService.getModelMetadata()
                
                setProgress(workDataOf("progress" to 20, "status" to "Downloading ML model"))
                // Download and save new ML model with progress tracking
                modelUpdated = downloadAndSaveModelWithProgress(modelMetadata.downloadUrl)
                
                if (modelUpdated) {
                    // Mark version as current
                    modelVersionManager.markVersionAsCurrent(modelMetadata.latestVersion)
                    modelVersionManager.storeDownloadUrl(modelMetadata.downloadUrl)
                    
                    // Reload the model
                    val modelFile = File(applicationContext.filesDir, "latest_model.tflite")
                    if (modelFile.exists()) {
                        economyPredictorTFLite.reloadModel(modelFile)
                        Log.i(TAG, "ML model hot-swapped successfully")
                    }
                }
            } else {
                Log.i(TAG, "ML model is up to date")
                setProgress(workDataOf("progress" to 30, "status" to "Model up to date"))
            }
            
            // Step 2: Sync economic data
            setProgress(workDataOf("progress" to 40, "status" to "Syncing economic data"))
            val dataUpdated = downloadAndSaveHistoricalData()
            
            if (dataUpdated) {
                Log.i(TAG, "Historical data updated")
            } else {
                Log.i(TAG, "No new economic data available")
            }
            
            setProgress(workDataOf("progress" to 100, "status" to "Sync completed"))
            
            if (modelUpdateRequired && modelUpdated || dataUpdated) {
                Result.success()
            } else if (!modelUpdateRequired && !dataUpdated) {
                Log.w(TAG, "No updates available during sync")
                Result.retry()
            } else {
                Result.success()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error during data synchronization", e)
            Result.retry()
        }
    }
    
    private suspend fun downloadAndSaveModelWithProgress(downloadUrl: String): Boolean {
        return try {
            val modelFile = File(applicationContext.filesDir, "latest_model.tflite")
            val success = downloadFileWithProgress(downloadUrl, modelFile)
            if (success) {
                Log.i(TAG, "ML model downloaded successfully to ${modelFile.absolutePath}")
            }
            success
        } catch (e: Exception) {
            Log.e(TAG, "Failed to download ML model", e)
            false
        }
    }
    
    private suspend fun downloadFileWithProgress(urlString: String, destinationFile: File): Boolean {
        return withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            var outputStream: FileOutputStream? = null
            
            try {
                val url = URL(urlString)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.requestMethod = "GET"
                
                // Get file size for progress tracking
                val contentLength = connection.contentLength
                setProgress(workDataOf("progress" to 25, "status" to "Starting download"))
                
                val responseCode = connection.responseCode
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.w(TAG, "Server returned HTTP $responseCode")
                    return@withContext false
                }
                
                inputStream = connection.inputStream
                outputStream = FileOutputStream(destinationFile)
                
                val buffer = ByteArray(8192)
                var bytesRead: Int
                var totalBytesRead: Int = 0
                
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    
                    // Update progress if we know the total size
                    if (contentLength > 0) {
                        val progress = 25 + ((totalBytesRead * 65) / contentLength).coerceAtMost(65)
                        setProgress(workDataOf("progress" to progress, "status" to "Downloading model ($totalBytesRead/$contentLength bytes)"))
                    }
                }
                
                outputStream.flush()
                Log.i(TAG, "File downloaded successfully: $urlString")
                true
            } catch (e: IOException) {
                Log.e(TAG, "Error downloading file from $urlString", e)
                false
            } finally {
                outputStream?.close()
                inputStream?.close()
                connection?.disconnect()
            }
        }
    }

    private suspend fun downloadAndSaveModel(): Boolean {
        return try {
            val modelFile = File(applicationContext.filesDir, "latest_model.tflite")
            val success = downloadFile(MODEL_DOWNLOAD_URL, modelFile)
            if (success) {
                Log.i(TAG, "ML model downloaded successfully to ${modelFile.absolutePath}")
            }
            success
        } catch (e: Exception) {
            Log.e(TAG, "Failed to download ML model", e)
            false
        }
    }

    private suspend fun downloadAndSaveHistoricalData(): Boolean {
        return try {
            // In a real implementation, we would parse the JSON and update Room DB
            // For now, we'll just simulate by regenerating mock data with new seed
            val historicalData = MockDataGenerator.generateHistoricalData()
            
            // Save to Room database via repository
            economicRepository.insertHistoricalData(historicalData)
            
            Log.i(TAG, "Historical data updated with new seed")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update historical data", e)
            false
        }
    }

    private suspend fun downloadFile(urlString: String, destinationFile: File): Boolean {
        return withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            var outputStream: FileOutputStream? = null
            
            try {
                val url = URL(urlString)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.requestMethod = "GET"
                
                val responseCode = connection.responseCode
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.w(TAG, "Server returned HTTP $responseCode")
                    return@withContext false
                }
                
                inputStream = connection.inputStream
                outputStream = FileOutputStream(destinationFile)
                
                val buffer = ByteArray(8192)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                
                outputStream.flush()
                Log.i(TAG, "File downloaded successfully: $urlString")
                true
            } catch (e: IOException) {
                Log.e(TAG, "Error downloading file from $urlString", e)
                false
            } finally {
                outputStream?.close()
                inputStream?.close()
                connection?.disconnect()
            }
        }
    }
}