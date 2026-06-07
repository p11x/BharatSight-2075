package com.bharatsight2075.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@HiltViewModel
class AskViewModel @Inject constructor() : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        
        val userMsg = ChatMessage(System.nanoTime().toString(), text, true)
        _messages.value = _messages.value + userMsg
        
        simulateAiResponse(text)
    }

    private fun simulateAiResponse(query: String) = viewModelScope.launch {
        _isTyping.value = true
        delay(2000) // Thinking...
        
        val responseText = when {
            query.contains("GDP", true) -> "India's projected GDP for 2075 is approximately $37.2 Trillion, driven by a 8.1% sustained growth rate and demographic dividend."
            query.contains("Inflation", true) -> "Current target inflation is 4.2%. The trajectory shows stability due to digitized supply chain efficiency."
            else -> "Processing economic data for '$query'. Strategic indicators suggest a bullish long-term trend for the manufacturing sector."
        }
        
        val aiMsg = ChatMessage(System.nanoTime().toString(), responseText, false)
        _messages.value = _messages.value + aiMsg
        _isTyping.value = false
    }
}
