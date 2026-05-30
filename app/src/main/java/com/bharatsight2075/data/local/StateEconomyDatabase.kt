package com.bharatsight2075.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface StateEconomyDao {
    @Query("SELECT * FROM states_economy")
    fun getAllStates(): Flow<List<StateEconomyEntity>>

    @Query("SELECT * FROM states_economy WHERE stateId = :id")
    suspend fun getStateById(id: String): StateEconomyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStates(states: List<StateEconomyEntity>)
}

@Database(entities = [StateEconomyEntity::class], version = 1, exportSchema = true)
abstract class StateEconomyDatabase : RoomDatabase() {
    abstract fun stateEconomyDao(): StateEconomyDao
}
