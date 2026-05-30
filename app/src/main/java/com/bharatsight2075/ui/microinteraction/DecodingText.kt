package com.bharatsight2075.ui.microinteraction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.fadeIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import kotlinx.coroutines.delay

private val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toList()

@Composable
fun DecodingText(
    text: String,
    modifier: Modifier = Modifier,
    typingSpeed: Long = 50L
) {
    var displayedText by remember { mutableStateOf("") }
    var isDecoding by remember { mutableStateOf(true) }
    
    LaunchedEffect(text) {
        isDecoding = true
        for (i in text.indices) {
            var charIndex = 0
            while (charIndex < 10) {
                displayedText = text.take(i + 1).dropLast(1) + CHARS.random()
                delay(20)
                charIndex++
            }
            displayedText = text.take(i + 1)
            delay(typingSpeed)
        }
        isDecoding = false
    }
    
    Text(
        text = displayedText,
        modifier = modifier,
        color = if (isDecoding) Color(0xFF00E5FF) else Color(0xFFFF9500),
        fontFamily = FontFamily.Monospace
    )
}