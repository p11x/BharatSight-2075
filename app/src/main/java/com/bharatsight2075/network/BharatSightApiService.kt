package com.bharatsight2075.network

import androidx.compose.runtime.Immutable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface BharatSightApiService {

    @GET("api/v1/model/metadata")
    suspend fun getModelMetadata(): ModelMetadataResponse

    @GET("api/v1/economy/historical")
    suspend fun getHistoricalData(
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<HistoricalEconomicData>

    @GET("api/v1/economy/predicted")
    suspend fun getPredictedData(
        @Query("daysAhead") daysAhead: Int
    ): List<PredictedEconomicData>

    @GET("api/v1/model/download/{version}")
    suspend fun downloadModel(
        @Path("version") version: String,
        @Header("Authorization") authToken: String
    ): ResponseBody
}

@Immutable
data class HistoricalEconomicData(
    val timestamp: Long,
    val gdpGrowth: Double,
    val inflationRate: Double,
    val unemploymentRate: Double = 0.0,
    val fiscalDeficit: Double = 0.0,
    val currentAccount: Double = 0.0
)

@Immutable
data class PredictedEconomicData(
    val timestamp: Long,
    val predictedGdpGrowth: Double,
    val confidenceInterval: Double = 0.0
)

@Immutable
data class ModelMetadataResponse(
    val latestVersion: String,
    val downloadUrl: String,
    val releaseNotes: String? = null,
    val fileSize: Long = 0L
)