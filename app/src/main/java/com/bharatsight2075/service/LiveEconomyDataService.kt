package com.bharatsight2075.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.*
import okio.ByteString
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class LiveMarketData(
    val symbol: String,
    val price: Double,
    val change: Double,
    val timestamp: Long = System.currentTimeMillis()
)

sealed class ConnectionStatus {
    object Connecting : ConnectionStatus()
    object Connected : ConnectionStatus()
    object Disconnected : ConnectionStatus()
    data class Error(val message: String) : ConnectionStatus()
}

@Singleton
class LiveEconomyDataService @Inject constructor(
    private val client: OkHttpClient
) {
    private val _marketData = MutableStateFlow<List<LiveMarketData>>(emptyList())
    val marketData = _marketData.asStateFlow()

    private val _connectionStatus = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionStatus = _connectionStatus.asStateFlow()

    private var webSocket: WebSocket? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private var retryCount = 0
    private val MAX_RETRIES = 5

    fun connect() {
        if (_connectionStatus.value == ConnectionStatus.Connected) return

        _connectionStatus.value = ConnectionStatus.Connecting
        val request = Request.Builder()
            .url("wss://streamer.finance.yahoo.com") // Placeholder for actual implementation
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                _connectionStatus.value = ConnectionStatus.Connected
                retryCount = 0
                // Subscribe to symbols
                webSocket.send("{\"subscribe\":[\"NSEI\",\"BSESN\",\"USDINR=X\"]}")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // Parse message and update flow
                // Example simulation
                updateMockData()
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                _connectionStatus.value = ConnectionStatus.Disconnected
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _connectionStatus.value = ConnectionStatus.Error(t.message ?: "Unknown error")
                attemptReconnect()
            }
        })
    }

    private fun attemptReconnect() {
        if (retryCount < MAX_RETRIES) {
            scope.launch {
                retryCount++
                val delayTime = Math.pow(2.0, retryCount.toDouble()).toLong() * 1000
                delay(delayTime)
                connect()
            }
        }
    }

    private fun updateMockData() {
        val current = _marketData.value.toMutableList()
        val symbols = listOf("NIFTY 50", "SENSEX", "USD/INR", "GOLD")
        val newData = symbols.map { symbol ->
            LiveMarketData(
                symbol = symbol,
                price = 24000.0 + (Math.random() * 100),
                change = (Math.random() * 2) - 1
            )
        }
        _marketData.value = newData
    }

    fun disconnect() {
        webSocket?.close(1000, "App closed")
        _connectionStatus.value = ConnectionStatus.Disconnected
    }
}
