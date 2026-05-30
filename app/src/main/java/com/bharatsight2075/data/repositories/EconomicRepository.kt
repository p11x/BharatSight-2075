package com.bharatsight2075.data.repositories

import com.bharatsight2075.data.local.EconomicDao
import com.bharatsight2075.data.local.HistoricalEconomicData
import com.bharatsight2075.data.local.PredictedEconomicData
import com.bharatsight2075.network.BharatSightApiService
import com.bharatsight2075.network.HistoricalEconomicData as ApiHistoricalData
import com.bharatsight2075.network.PredictedEconomicData as ApiPredictedData
import com.bharatsight2075.network.ModelMetadataResponse
import com.bharatsight2075.util.ModelVersionManager
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class EconomicRepository @Inject constructor(
    private val dao: EconomicDao,
    private val apiService: BharatSightApiService,
    private val modelVersionManager: ModelVersionManager
) {
    
    // Expose Room data as Flows (Single Source of Truth)
    val historicalData: Flow<List<HistoricalEconomicData>> = dao.getAllHistoricalData()
    val predictedData: Flow<List<PredictedEconomicData>> = dao.getAllPredictedData()

    suspend fun insertHistoricalData(data: List<HistoricalEconomicData>) {
        dao.insertHistoricalData(data)
    }

    suspend fun insertPredictedData(data: List<PredictedEconomicData>) {
        dao.insertPredictedData(data)
    }

    suspend fun clearHistoricalData() {
        dao.clearHistoricalData()
    }

    suspend fun clearPredictedData() {
        dao.clearPredictedData()
    }

    /**
     * Offline-first synchronization: Fetch from API and update local Room database
     * Returns Result indicating success or failure
     */
    suspend fun syncEconomyData(): Boolean {
        try {
            // Check if we need to download a new model version
            val modelUpdateRequired = modelVersionManager.isUpdateRequired()
            
            if (modelUpdateRequired) {
                // Fetch model metadata to get version and download URL
                val modelMetadata = apiService.getModelMetadata()
                
                // Store the download URL for the worker to use
                modelVersionManager.storeDownloadUrl(modelMetadata.downloadUrl)
                // Note: Actual version marking happens in the worker after successful download
            }
            
            // Sync economic data (Room is the Single Source of Truth)
            val historicalData = apiService.getHistoricalData(null, null)
            val predictedData = apiService.getPredictedData(30) // Next 30 days
            
            // Clear stale data and insert fresh data
            clearHistoricalData()
            clearPredictedData()
            
            // Convert API entities to Room entities
            val roomHistoricalData = historicalData.map { apiData ->
                HistoricalEconomicData(
                    year = apiData.timestamp.toInt(),
                    gdp = apiData.gdpGrowth,
                    inflation = apiData.inflationRate,
                    fdi = 0.0, // Placeholder - would come from API
                    sectorAgri = 0.0, // Placeholder
                    sectorMfg = 0.0, // Placeholder
                    sectorSvc = 0.0, // Placeholder
                    population = 0.0 // Placeholder
                )
            }.toList()
            
            val roomPredictedData = predictedData.map { apiData ->
                PredictedEconomicData(
                    year = apiData.timestamp.toInt(),
                    gdp = apiData.predictedGdpGrowth,
                    inflation = 0.0, // Placeholder
                    fdi = 0.0, // Placeholder
                    sectorAgri = 0.0, // Placeholder
                    sectorMfg = 0.0, // Placeholder
                    sectorSvc = 0.0, // Placeholder
                    population = 0.0 // Placeholder
                )
            }.toList()
            
            insertHistoricalData(roomHistoricalData)
            insertPredictedData(roomPredictedData)
            
            return true
        } catch (e: Exception) {
            // In a real app, we might want to handle specific exceptions differently
            return false
        }
    }
}