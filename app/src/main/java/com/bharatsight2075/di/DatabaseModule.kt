package com.bharatsight2075.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bharatsight2075.data.local.AppDatabase
import com.bharatsight2075.data.local.EconomicDao
import com.bharatsight2075.data.local.StateEconomyDao
import com.bharatsight2075.data.local.StateEconomyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Migration logic for version 1 to 2
            // Example: db.execSQL("ALTER TABLE ... ADD COLUMN ...")
        }
    }

    @Provides
    @Singleton
    fun provideSupportFactory(): SupportFactory {
        val passphrase = SQLiteDatabase.getBytes("bharatsight_2075_secure_key".toCharArray())
        return SupportFactory(passphrase)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        factory: SupportFactory
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bharatsight2075.db"
        )
        .openHelperFactory(factory)
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()
    }

    @Provides
    @Singleton
    fun provideEconomicDao(database: AppDatabase): EconomicDao {
        return database.economicDao()
    }

    @Provides
    @Singleton
    fun provideStateDatabase(
        @ApplicationContext context: Context,
        factory: SupportFactory
    ): StateEconomyDatabase {
        return Room.databaseBuilder(
            context,
            StateEconomyDatabase::class.java,
            "states_economy.db"
        )
        .openHelperFactory(factory)
        .build()
    }

    @Provides
    @Singleton
    fun provideStateEconomyDao(database: StateEconomyDatabase): StateEconomyDao {
        return database.stateEconomyDao()
    }
}
