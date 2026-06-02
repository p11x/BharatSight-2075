package com.bharatsight2075.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val isError: Boolean = false
)

@HiltViewModel
class EconomyQueryViewModel @Inject constructor() : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage("SYSTEM INITIALIZED. AWAITING QUERY...", false)
    ))
    val messages = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()

    // Gemini Nano setup (requires actual API key or AICore integration)
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "YOUR_API_KEY" // Placeholder
    )

    fun sendQuery(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            _messages.value += ChatMessage(query, true)
            _isTyping.value = true

            try {
                val prompt = "You are an economic expert for India 2075. Context: India's GDP is growing at 8% and tech is booming. Question: $query"
                val response = generativeModel.generateContent(prompt)
                
                _messages.value += ChatMessage(response.text ?: "NO DATA RETURNED.", false)
            } catch (e: Exception) {
                _messages.value += ChatMessage("ERROR: ${e.localizedMessage}", false, true)
            } finally {
                _isTyping.value = false
            }
        }
    }
}
