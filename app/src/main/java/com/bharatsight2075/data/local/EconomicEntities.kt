package com.bharatsight2075.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historical_economic_data")
data class HistoricalEconomicData(
    @PrimaryKey val year: Int,
    val gdp: Double,
    val inflation: Double,
    val fdi: Double,
    val sectorAgri: Double,
    val sectorMfg: Double,
    val sectorSvc: Double,
    val population: Double
)

@Entity(tableName = "predicted_economic_data")
data class PredictedEconomicData(
    @PrimaryKey val year: Int,
    val gdp: Double,
    val inflation: Double,
    val fdi: Double,
    val sectorAgri: Double,
    val sectorMfg: Double,
    val sectorSvc: Double,
    val population: Double
)