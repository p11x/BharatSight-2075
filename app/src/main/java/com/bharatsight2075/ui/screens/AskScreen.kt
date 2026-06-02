package com.bharatsight2075.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.microinteraction.DecodingText
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.viewmodel.ChatMessage
import com.bharatsight2075.viewmodel.EconomyQueryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskScreen(
    navController: NavController,
    viewModel: EconomyQueryViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    var textInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val extendedColors = SciFiTheme.extendedColors
    
    BackHandler {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.HOME) { inclusive = true }
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Ask BharatSight AI",
                    badge = "GEMINI NANO",
                    badgeColor = Color(0xFF7C4DFF),
                    onBackClick = { /* Handled by filter */ }
                )
            )
        }
    ) { padding ->
        GridBackgroundSurface(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                
                if (messages.isEmpty() || (messages.size == 1 && messages[0].text.contains("INITIALIZED"))) {
                    WelcomeState(onSuggestionClick = { 
                        textInput = it
                        viewModel.sendQuery(it)
                        textInput = ""
                    })
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    reverseLayout = true
                ) {
                    if (isTyping) {
                        item {
                            TypingIndicator()
                        }
                    }
                    items(messages.reversed()) { message ->
                        ChatMessageBubble(message)
                    }
                }

                ChatInputRow(
                    textInput = textInput,
                    onValueChange = { textInput = it },
                    onSend = {
                        viewModel.sendQuery(textInput)
                        textInput = ""
                    }
                )
            }
        }
    }
}

@Composable
fun WelcomeState(onSuggestionClick: (String) -> Unit) {
    val extendedColors = SciFiTheme.extendedColors
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "Rupee")
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 360f,
            animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing)), label = "Rotation"
        )
        
        Text(
            text = "₹",
            fontSize = 56.sp,
            color = extendedColors.primary,
            modifier = Modifier.graphicsLayer { rotationY = rotation }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            "Ask me anything about India's economy",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        val suggestions = listOf(
            "📈 GDP forecast 2075", "🏦 Compare India vs China",
            "💹 Impact of repo rate hike", "🌍 India's top exports"
        )
        
        suggestions.forEach { suggestion ->
            SuggestionChip(suggestion) { onSuggestionClick(suggestion) }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SuggestionChip(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = SciFiTheme.extendedColors.surface.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SciFiTheme.extendedColors.primary.copy(alpha = 0.2f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            fontSize = 13.sp,
            color = Color.White
        )
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    val extendedColors = SciFiTheme.extendedColors
    val isUser = message.isUser

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            if (!isUser) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(extendedColors.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("AI", fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            Surface(
                color = if (isUser) extendedColors.accent.copy(alpha = 0.15f) else extendedColors.primary.copy(alpha = 0.08f),
                shape = RoundedCornerShape(
                    topStart = if (isUser) 16.dp else 4.dp,
                    topEnd = if (isUser) 4.dp else 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, 
                    if (isUser) extendedColors.accent.copy(alpha = 0.5f) else extendedColors.primary.copy(alpha = 0.3f)
                ),
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                if (isUser) {
                    Text(
                        text = message.text,
                        modifier = Modifier.padding(12.dp),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                } else {
                    DecodingText(
                        text = message.text,
                        modifier = Modifier.padding(12.dp),
                        typingSpeed = 20L
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "12:45 PM", // Mock timestamp
            fontSize = 9.sp,
            color = Color.White.copy(alpha = 0.35f),
            modifier = Modifier.padding(horizontal = if (isUser) 0.dp else 20.dp)
        )
    }
}

@Composable
fun ChatInputRow(
    textInput: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .imePadding()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = textInput,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp)),
                placeholder = {
                    Text("Ask about India's economy...", fontSize = 13.sp, color = Color.White.copy(alpha = 0.4f))
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Mic,
                        contentDescription = "Voice",
                        tint = extendedColors.primary.copy(alpha = 0.6f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = extendedColors.surface,
                    focusedContainerColor = extendedColors.surface,
                    unfocusedBorderColor = extendedColors.primary.copy(alpha = 0.4f),
                    focusedBorderColor = extendedColors.primary
                ),
                shape = RoundedCornerShape(28.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { onSend() })
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FloatingActionButton(
                onClick = onSend,
                modifier = Modifier.size(44.dp),
                containerColor = extendedColors.primary,
                contentColor = Color.Black,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun TypingIndicator() {
    Text(
        "AI IS ANALYZING...",
        color = SciFiTheme.extendedColors.primary,
        fontSize = 10.sp,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.padding(start = 20.dp)
    )
}
