package com.bharatsight2075.data.local

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "states_economy")
data class StateEconomyEntity(
    @PrimaryKey val stateId: String,
    val name: String,
    val gsdp: Double,
    val population: Long,
    val literacyRate: Double,
    val hdi: Double,
    val unemployment: Double,
    val dominantSector: String,
    val fdiInflow: Double,
    val growthRate: Double
)

@Immutable
data class StateTrend(
    val stateId: String,
    val year: Int,
    val value: Double
)
