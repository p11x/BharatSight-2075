package com.bharatsight2075.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        HistoricalEconomicData::class, 
        PredictedEconomicData::class,
        StateEconomyEntity::class // Added missing entity
    ],
    version = 2, // Incremented version
    exportSchema = true // Enabled exportSchema
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun economicDao(): EconomicDao
}