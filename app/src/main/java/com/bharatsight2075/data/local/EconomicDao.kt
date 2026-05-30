package com.bharatsight2075.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EconomicDao {
    @Query("SELECT * FROM historical_economic_data ORDER BY year ASC")
    fun getAllHistoricalData(): Flow<List<HistoricalEconomicData>>

    @Query("SELECT * FROM predicted_economic_data ORDER BY year ASC")
    fun getAllPredictedData(): Flow<List<PredictedEconomicData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoricalData(data: List<HistoricalEconomicData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPredictedData(data: List<PredictedEconomicData>)

    @Query("DELETE FROM historical_economic_data")
    suspend fun clearHistoricalData(): Int

    @Query("DELETE FROM predicted_economic_data")
    suspend fun clearPredictedData(): Int
}