package com.bharatsight2075.ui.microinteraction

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay

private val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toList()

@Composable
fun DecodingText(
    text: String,
    modifier: Modifier = Modifier,
    typingSpeed: Long = 50L,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    var displayedText by remember { mutableStateOf("") }
    var isDecoding by remember { mutableStateOf(true) }
    
    LaunchedEffect(text) {
        isDecoding = true
        for (i in text.indices) {
            var charIndex = 0
            while (charIndex < 5) { // Reduced cycles for faster feel
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
        color = if (color != Color.Unspecified) color else if (isDecoding) Color(0xFF00E5FF) else Color(0xFFFF9500),
        fontSize = fontSize,
        fontFamily = FontFamily.Monospace,
        style = style
    )
}