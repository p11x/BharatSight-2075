package com.bharatsight2075.data.local

import kotlin.random.Random

object MockDataGenerator {
    
    fun generateHistoricalData(): List<HistoricalEconomicData> {
        val data = mutableListOf<HistoricalEconomicData>()
        val random = Random(EconomicConstants.RANDOM_SEED_HISTORICAL)
        
        var baseGdp = EconomicConstants.BASE_GDP_START
        var population = EconomicConstants.BASE_POPULATION_START
        
        for (year in EconomicConstants.HISTORICAL_START_YEAR..EconomicConstants.HISTORICAL_END_YEAR) {
            val gdpGrowth = calculateHistoricalGdpGrowth(year, random)
            baseGdp *= (1 + gdpGrowth)
            
            val populationGrowth = calculatePopulationGrowth(random)
            population *= (1 + populationGrowth)
            
            val inflation = calculateHistoricalInflation(year, random)
            val fdi = calculateHistoricalFdi(year, random)
            
            val sectorAgri = calculateSectorAgri(population, random)
            val sectorMfg = calculateSectorMfg(population, random)
            val sectorSvc = calculateSectorSvc(population, random)
            
            data.add(
                HistoricalEconomicData(
                    year = year,
                    gdp = maxOf(0.0, baseGdp),
                    inflation = inflation,
                    fdi = maxOf(0.0, fdi),
                    sectorAgri = maxOf(0.0, sectorAgri),
                    sectorMfg = maxOf(0.0, sectorMfg),
                    sectorSvc = maxOf(0.0, sectorSvc),
                    population = maxOf(0.0, population)
                )
            )
        }
        
        return data
    }
    
    private fun calculateHistoricalGdpGrowth(year: Int, random: Random): Double {
        return when {
            year < 1980 -> EconomicConstants.GDP_GROWTH_1950_1980_BASE + random.nextDouble(
                -EconomicConstants.GDP_GROWTH_VARIANCE_1950_1980,
                EconomicConstants.GDP_GROWTH_VARIANCE_1950_1980
            )
            year < 2000 -> EconomicConstants.GDP_GROWTH_1980_2000_BASE + random.nextDouble(
                -EconomicConstants.GDP_GROWTH_VARIANCE_1980_2000,
                EconomicConstants.GDP_GROWTH_VARIANCE_1980_2000
            )
            year < 2020 -> EconomicConstants.GDP_GROWTH_2000_2020_BASE + random.nextDouble(
                -EconomicConstants.GDP_GROWTH_VARIANCE_2000_2020,
                EconomicConstants.GDP_GROWTH_VARIANCE_2000_2020
            )
            else -> EconomicConstants.GDP_GROWTH_2020_PLUS_BASE + random.nextDouble(
                -EconomicConstants.GDP_GROWTH_VARIANCE_2020_PLUS,
                EconomicConstants.GDP_GROWTH_VARIANCE_2020_PLUS
            )
        }
    }
    
    private fun calculatePopulationGrowth(random: Random): Double {
        return EconomicConstants.POPULATION_GROWTH_BASE + random.nextDouble(
            -EconomicConstants.POPULATION_GROWTH_VARIANCE_MINUS,
            EconomicConstants.POPULATION_GROWTH_VARIANCE_PLUS
        )
    }
    
    private fun calculateHistoricalInflation(year: Int, random: Random): Double {
        return when {
            year < 1970 -> EconomicConstants.INFLATION_PRE_1970_BASE + random.nextDouble(
                -EconomicConstants.INFLATION_VARIANCE_PRE_1970_MINUS,
                EconomicConstants.INFLATION_VARIANCE_PRE_1970_PLUS
            )
            year < 1990 -> EconomicConstants.INFLATION_1970_1990_BASE + random.nextDouble(
                -EconomicConstants.INFLATION_VARIANCE_1970_1990_MINUS,
                EconomicConstants.INFLATION_VARIANCE_1970_1990_PLUS
            )
            year < 2010 -> EconomicConstants.INFLATION_1990_2010_BASE + random.nextDouble(
                -EconomicConstants.INFLATION_VARIANCE_1990_2010_MINUS,
                EconomicConstants.INFLATION_VARIANCE_1990_2010_PLUS
            )
            else -> EconomicConstants.INFLATION_POST_2010_BASE + random.nextDouble(
                -EconomicConstants.INFLATION_VARIANCE_POST_2010_MINUS,
                EconomicConstants.INFLATION_VARIANCE_POST_2010_PLUS
            )
        }
    }
    
    private fun calculateHistoricalFdi(year: Int, random: Random): Double {
        return when {
            year < 1991 -> EconomicConstants.FDI_PRE_1991_BASE + random.nextDouble(
                EconomicConstants.FDI_PRE_1991_VARIANCE_MINUS,
                EconomicConstants.FDI_PRE_1991_VARIANCE_PLUS
            )
            else -> EconomicConstants.FDI_POST_1991_BASE + random.nextDouble(
                EconomicConstants.FDI_POST_1991_VARIANCE_MINUS,
                EconomicConstants.FDI_POST_1991_VARIANCE_PLUS
            ) * ((year - 1991) / EconomicConstants.FDI_YEARS_ADJUSTMENT)
        }
    }
    
    private fun calculateSectorAgri(population: Double, random: Random): Double {
        return (EconomicConstants.SECTOR_AGRI_DIVISOR / population) + random.nextDouble(
            EconomicConstants.SECTOR_VARIANCE_AGRI_MINUS,
            EconomicConstants.SECTOR_VARIANCE_AGRI_PLUS
        )
    }
    
    private fun calculateSectorMfg(population: Double, random: Random): Double {
        return (EconomicConstants.SECTOR_MFG_DIVISOR / population) + random.nextDouble(
            EconomicConstants.SECTOR_VARIANCE_MFG_MINUS,
            EconomicConstants.SECTOR_VARIANCE_MFG_PLUS
        )
    }
    
    private fun calculateSectorSvc(population: Double, random: Random): Double {
        return (EconomicConstants.SECTOR_SVC_DIVISOR / population) + random.nextDouble(
            EconomicConstants.SECTOR_VARIANCE_SVC_MINUS,
            EconomicConstants.SECTOR_VARIANCE_SVC_PLUS
        )
    }
    
    fun generatePredictedData(
        baseData: List<HistoricalEconomicData>,
        policyImpact: PolicyImpact = PolicyImpact()
    ): List<PredictedEconomicData> {
        val data = mutableListOf<PredictedEconomicData>()
        val random = Random(EconomicConstants.RANDOM_SEED_PREDICTION)
        
        val lastHistorical = baseData.last()
        var baseGdp = lastHistorical.gdp
        var population = lastHistorical.population
        
        for (year in EconomicConstants.PREDICTION_START_YEAR..EconomicConstants.PREDICTION_END_YEAR) {
            val yearsSince2025 = year - EconomicConstants.HISTORICAL_END_YEAR
            
            val policyMultiplier = calculatePolicyMultiplier(policyImpact)
            val gdpGrowth = calculatePredictionGdpGrowth(policyMultiplier, random)
            baseGdp *= (1 + gdpGrowth)
            
            val populationGrowth = calculatePredictionPopulationGrowth(random)
            population *= (1 + populationGrowth)
            
            val inflation = calculatePredictionInflation(random)
            val fdi = calculatePredictionFdi(lastHistorical.fdi, yearsSince2025, random)
            
            val agriFactor = calculatePredictionAgriFactor(yearsSince2025, random)
            val mfgFactor = calculatePredictionMfgFactor(yearsSince2025, random)
            val svcFactor = calculatePredictionSvcFactor(yearsSince2025, random)
            
            data.add(
                PredictedEconomicData(
                    year = year,
                    gdp = maxOf(0.0, baseGdp),
                    inflation = inflation,
                    fdi = maxOf(0.0, fdi),
                    sectorAgri = maxOf(0.0, calculatePredictionSectorAgri(lastHistorical.sectorAgri, agriFactor, random)),
                    sectorMfg = maxOf(0.0, calculatePredictionSectorMfg(lastHistorical.sectorMfg, mfgFactor, random)),
                    sectorSvc = maxOf(0.0, calculatePredictionSectorSvc(lastHistorical.sectorSvc, svcFactor, random)),
                    population = maxOf(0.0, population)
                )
            )
        }
        
        return data
    }
    
    private fun calculatePolicyMultiplier(policyImpact: PolicyImpact): Double {
        return 1.0 + 
            (policyImpact.taxRate - 0.5) * EconomicConstants.POLICY_TAX_RATE_MULTIPLIER +
            (policyImpact.infrastructure - 0.5) * EconomicConstants.POLICY_INFRASTRUCTURE_MULTIPLIER +
            (policyImpact.education - 0.5) * EconomicConstants.POLICY_EDUCATION_MULTIPLIER +
            (policyImpact.foreignPolicy - 0.5) * EconomicConstants.POLICY_FOREIGN_POLICY_MULTIPLIER
    }
    
    private fun calculatePredictionGdpGrowth(policyMultiplier: Double, random: Random): Double {
        return EconomicConstants.PREDICTION_GDP_GROWTH_BASE * policyMultiplier + random.nextDouble(
            EconomicConstants.PREDICTION_GDP_GROWTH_VARIANCE_MINUS,
            EconomicConstants.PREDICTION_GDP_GROWTH_VARIANCE_PLUS
        )
    }
    
    private fun calculatePredictionPopulationGrowth(random: Random): Double {
        return EconomicConstants.PREDICTION_POPULATION_GROWTH_BASE + random.nextDouble(
            EconomicConstants.PREDICTION_POPULATION_GROWTH_VARIANCE_MINUS,
            EconomicConstants.PREDICTION_POPULATION_GROWTH_VARIANCE_PLUS
        )
    }
    
    private fun calculatePredictionInflation(random: Random): Double {
        return EconomicConstants.PREDICTION_INFLATION_BASE + random.nextDouble(
            EconomicConstants.PREDICTION_INFLATION_VARIANCE_MINUS,
            EconomicConstants.PREDICTION_INFLATION_VARIANCE_PLUS
        )
    }
    
    private fun calculatePredictionFdi(baseFdi: Double, yearsSince2025: Int, random: Random): Double {
        return baseFdi * (1 + EconomicConstants.PREDICTION_FDI_GROWTH_RATE * yearsSince2025 + random.nextDouble(
            EconomicConstants.PREDICTION_FDI_VARIANCE_MINUS,
            EconomicConstants.PREDICTION_FDI_VARIANCE_PLUS
        ))
    }
    
    private fun calculatePredictionAgriFactor(yearsSince2025: Int, random: Random): Double {
        return EconomicConstants.PREDICTION_AGRI_FACTOR_BASE + 
            (yearsSince2025 * EconomicConstants.PREDICTION_AGRI_FACTOR_YEAR_MULTIPLIER) + 
            random.nextDouble(
                EconomicConstants.SECTOR_PREDICTION_VARIANCE_AGRI_MINUS,
                EconomicConstants.SECTOR_PREDICTION_VARIANCE_AGRI_PLUS
            )
    }
    
    private fun calculatePredictionMfgFactor(yearsSince2025: Int, random: Random): Double {
        return EconomicConstants.PREDICTION_MFG_FACTOR_BASE + 
            (yearsSince2025 * EconomicConstants.PREDICTION_MFG_FACTOR_YEAR_MULTIPLIER) + 
            random.nextDouble(
                EconomicConstants.SECTOR_PREDICTION_VARIANCE_MFG_MINUS,
                EconomicConstants.SECTOR_PREDICTION_VARIANCE_MFG_PLUS
            )
    }
    
    private fun calculatePredictionSvcFactor(yearsSince2025: Int, random: Random): Double {
        return EconomicConstants.PREDICTION_SVC_FACTOR_BASE + 
            (yearsSince2025 * EconomicConstants.PREDICTION_SVC_FACTOR_YEAR_MULTIPLIER) + 
            random.nextDouble(
                EconomicConstants.SECTOR_PREDICTION_VARIANCE_SVC_MINUS,
                EconomicConstants.SECTOR_PREDICTION_VARIANCE_SVC_PLUS
            )
    }
    
    private fun calculatePredictionSectorAgri(baseValue: Double, factor: Double, random: Random): Double {
        return (baseValue * factor) + random.nextDouble(
            EconomicConstants.SECTOR_PREDICTION_NOISE_AGRI_MINUS,
            EconomicConstants.SECTOR_PREDICTION_NOISE_AGRI_PLUS
        )
    }
    
    private fun calculatePredictionSectorMfg(baseValue: Double, factor: Double, random: Random): Double {
        return (baseValue * factor) + random.nextDouble(
            EconomicConstants.SECTOR_PREDICTION_NOISE_MFG_MINUS,
            EconomicConstants.SECTOR_PREDICTION_NOISE_MFG_PLUS
        )
    }
    
    private fun calculatePredictionSectorSvc(baseValue: Double, factor: Double, random: Random): Double {
        return (baseValue * factor) + random.nextDouble(
            EconomicConstants.SECTOR_PREDICTION_NOISE_SVC_MINUS,
            EconomicConstants.SECTOR_PREDICTION_NOISE_SVC_PLUS
        )
    }
}

data class PolicyImpact(
    val taxRate: Double = 0.5,
    val infrastructure: Double = 0.5,
    val education: Double = 0.5,
    val foreignPolicy: Double = 0.5
)