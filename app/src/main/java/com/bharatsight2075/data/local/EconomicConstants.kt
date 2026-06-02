package com.bharatsight2075.data.local

/**
 * Centralized constants for economic data generation.
 * Contains all magic numbers used in MockDataGenerator for better maintainability.
 */
object EconomicConstants {
    // Historical data generation constants
    const val BASE_GDP_START = 32.5
    const val BASE_POPULATION_START = 376.0
    const val HISTORICAL_START_YEAR = 1950
    const val HISTORICAL_END_YEAR = 2025
    const val PREDICTION_START_YEAR = 2026
    const val PREDICTION_END_YEAR = 2075
    
    // GDP growth rates by era
    const val GDP_GROWTH_1950_1980_BASE = 0.03
    const val GDP_GROWTH_1980_2000_BASE = 0.06
    const val GDP_GROWTH_2000_2020_BASE = 0.07
    const val GDP_GROWTH_2020_PLUS_BASE = 0.08
    const val GDP_GROWTH_VARIANCE_1950_1980 = 0.02
    const val GDP_GROWTH_VARIANCE_1980_2000 = 0.03
    const val GDP_GROWTH_VARIANCE_2000_2020 = 0.04
    const val GDP_GROWTH_VARIANCE_2020_PLUS = 0.03
    
    // Population growth
    const val POPULATION_GROWTH_BASE = 0.018
    const val POPULATION_GROWTH_VARIANCE_MINUS = 0.003
    const val POPULATION_GROWTH_VARIANCE_PLUS = 0.005
    
    // Inflation rates by era
    const val INFLATION_PRE_1970_BASE = 5.0
    const val INFLATION_1970_1990_BASE = 8.0
    const val INFLATION_1990_2010_BASE = 6.0
    const val INFLATION_POST_2010_BASE = 4.0
    const val INFLATION_VARIANCE_PRE_1970_MINUS = 2.0
    const val INFLATION_VARIANCE_PRE_1970_PLUS = 3.0
    const val INFLATION_VARIANCE_1970_1990_MINUS = 3.0
    const val INFLATION_VARIANCE_1970_1990_PLUS = 4.0
    const val INFLATION_VARIANCE_1990_2010_MINUS = 4.0
    const val INFLATION_VARIANCE_1990_2010_PLUS = 3.0
    const val INFLATION_VARIANCE_POST_2010_MINUS = 1.5
    const val INFLATION_VARIANCE_POST_2010_PLUS = 2.5
    
    // FDI constants
    const val FDI_PRE_1991_BASE = 0.1
    const val FDI_PRE_1991_VARIANCE_MINUS = 0.0
    const val FDI_PRE_1991_VARIANCE_PLUS = 0.1
    const val FDI_POST_1991_BASE = 20.0
    const val FDI_POST_1991_VARIANCE_MINUS = -5.0
    const val FDI_POST_1991_VARIANCE_PLUS = 10.0
    const val FDI_YEARS_ADJUSTMENT = 34.0
    
    // Sector contribution divisors
    const val SECTOR_AGRI_DIVISOR = 180.0
    const val SECTOR_MFG_DIVISOR = 150.0
    const val SECTOR_SVC_DIVISOR = 100.0
    const val SECTOR_VARIANCE_AGRI_MINUS = -5.0
    const val SECTOR_VARIANCE_AGRI_PLUS = 5.0
    const val SECTOR_VARIANCE_MFG_MINUS = -3.0
    const val SECTOR_VARIANCE_MFG_PLUS = 3.0
    const val SECTOR_VARIANCE_SVC_MINUS = -2.0
    const val SECTOR_VARIANCE_SVC_PLUS = 2.0
    
    // Prediction constants
    const val PREDICTION_GDP_GROWTH_BASE = 0.09
    const val PREDICTION_GDP_GROWTH_VARIANCE_MINUS = -0.02
    const val PREDICTION_GDP_GROWTH_VARIANCE_PLUS = 0.03
    const val PREDICTION_POPULATION_GROWTH_BASE = 0.012
    const val PREDICTION_POPULATION_GROWTH_VARIANCE_MINUS = -0.002
    const val PREDICTION_POPULATION_GROWTH_VARIANCE_PLUS = 0.004
    const val PREDICTION_INFLATION_BASE = 3.5
    const val PREDICTION_INFLATION_VARIANCE_MINUS = -0.5
    const val PREDICTION_INFLATION_VARIANCE_PLUS = 1.5
    const val PREDICTION_FDI_GROWTH_RATE = 0.05
    const val PREDICTION_FDI_VARIANCE_MINUS = -0.1
    const val PREDICTION_FDI_VARIANCE_PLUS = 0.1
    
    // Policy impact multipliers
    const val POLICY_TAX_RATE_MULTIPLIER = 0.1
    const val POLICY_INFRASTRUCTURE_MULTIPLIER = 0.3
    const val POLICY_EDUCATION_MULTIPLIER = 0.2
    const val POLICY_FOREIGN_POLICY_MULTIPLIER = 0.15
    
    // Sector prediction factors
    const val PREDICTION_AGRI_FACTOR_BASE = 1.0
    const val PREDICTION_AGRI_FACTOR_YEAR_MULTIPLIER = -0.02
    const val PREDICTION_MFG_FACTOR_BASE = 1.0
    const val PREDICTION_MFG_FACTOR_YEAR_MULTIPLIER = 0.03
    const val PREDICTION_SVC_FACTOR_BASE = 1.0
    const val PREDICTION_SVC_FACTOR_YEAR_MULTIPLIER = 0.05
    const val SECTOR_PREDICTION_VARIANCE_AGRI_MINUS = -0.01
    const val SECTOR_PREDICTION_VARIANCE_AGRI_PLUS = 0.01
    const val SECTOR_PREDICTION_VARIANCE_MFG_MINUS = -0.02
    const val SECTOR_PREDICTION_VARIANCE_MFG_PLUS = 0.02
    const val SECTOR_PREDICTION_VARIANCE_SVC_MINUS = -0.03
    const val SECTOR_PREDICTION_VARIANCE_SVC_PLUS = 0.03
    const val SECTOR_PREDICTION_NOISE_AGRI_MINUS = -1.0
    const val SECTOR_PREDICTION_NOISE_AGRI_PLUS = 1.0
    const val SECTOR_PREDICTION_NOISE_MFG_MINUS = -1.0
    const val SECTOR_PREDICTION_NOISE_MFG_PLUS = 1.0
    const val SECTOR_PREDICTION_NOISE_SVC_MINUS = -2.0
    const val SECTOR_PREDICTION_NOISE_SVC_PLUS = 2.0
    
    // Random seeds
    const val RANDOM_SEED_HISTORICAL = 42
    const val RANDOM_SEED_PREDICTION = 123
}