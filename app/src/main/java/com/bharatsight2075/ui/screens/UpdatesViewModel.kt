package com.bharatsight2075.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

data class MarketUpdate(
    val category: String,
    val name: String,
    val icon: ImageVector,
    var value: String,
    var change: Double,
    var changePct: Double,
    val timestamp: String,
    val history: List<Float>
)

@HiltViewModel
class UpdatesViewModel @Inject constructor() : ViewModel() {
    private val _updates = MutableStateFlow<List<MarketUpdate>>(emptyList())
    val updates: StateFlow<List<MarketUpdate>> = _updates.asStateFlow()

    private val _selectedCategory = MutableStateFlow("ALL")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    init {
        loadInitialData()
        startPriceUpdateLoop()
    }

    private fun loadInitialData() {
        _updates.value = listOf(
            MarketUpdate("CURRENCY", "INR/USD", Icons.Outlined.ShowChart, "83.24", 0.05, 0.06, "10:30", List(20) { Random.nextFloat() }),
            MarketUpdate("EQUITY", "NIFTY 50", Icons.Outlined.ShowChart, "24,542", 124.5, 0.51, "10:30", List(20) { Random.nextFloat() }),
            MarketUpdate("COMMODITY", "GOLD (10g)", Icons.Outlined.ShowChart, "72,140", -340.0, -0.45, "10:30", List(20) { Random.nextFloat() }),
            MarketUpdate("CRYPTO", "BITCOIN", Icons.Outlined.ShowChart, "64,210", 1200.0, 1.8, "10:30", List(20) { Random.nextFloat() }),
            MarketUpdate("EQUITY", "SENSEX", Icons.Outlined.ShowChart, "80,124", 450.2, 0.56, "10:30", List(20) { Random.nextFloat() })
        )
    }

    private fun startPriceUpdateLoop() = viewModelScope.launch {
        while (true) {
            delay(3000)
            _updates.value = _updates.value.map { update ->
                val currentVal = update.value.replace(",", "").toDoubleOrNull() ?: 100.0
                val delta = (Random.nextDouble() - 0.5) * (currentVal * 0.001)
                val newVal = currentVal + delta
                update.copy(
                    value = String.format(Locale.UK, "%.2f", newVal),
                    change = delta,
                    changePct = (delta / currentVal) * 100
                )
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }
}
