package com.bharatsight2075.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightLogo
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.microinteraction.DecodingText
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun AskScreen(
    navController: NavController,
    viewModel: AskViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val isTyping by viewModel.isTyping.collectAsStateWithLifecycle()
    val extendedColors = SciFiTheme.extendedColors
    val listState = rememberLazyListState()
    
    var inputText by remember { mutableStateOf("") }

    LaunchedEffect(messages.size, isTyping) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size)
        }
    }

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Ask BharatSight AI",
                    badge = "GEMINI",
                    badgeColor = Color(0xFF7C4DFF),
                    onBackClick = { /* Root */ }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (messages.isEmpty()) {
                WelcomeState(onSuggestion = { viewModel.sendMessage(it) }, extendedColors)
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(messages) { msg ->
                        ChatBubble(msg, extendedColors)
                    }
                    if (isTyping) {
                        item { TypingIndicator(extendedColors) }
                    }
                }
            }
            
            // Input Row
            InputRow(
                text = inputText,
                onTextChange = { inputText = it },
                onSend = {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                },
                colors = extendedColors
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WelcomeState(onSuggestion: (String) -> Unit, colors: SciFiTheme.SciFiColors) {
    val suggestions = listOf("GDP 2075 projections", "Monsoon impact on Agri", "Digital Rupee updates", "FDI inflow by state", "Smart City milestones", "Inflation trends")
    
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BharatSightLogo(modifier = Modifier.size(160.dp))
        Spacer(Modifier.height(24.dp))
        Text("HOW CAN I ASSIST YOUR ANALYSIS?", style = SciFiTheme.typography.SectionHead, color = colors.primary)
        Spacer(Modifier.height(32.dp))
        
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 2
        ) {
            suggestions.forEach { text ->
                SuggestionChip(text, onClick = { onSuggestion(text) }, colors)
            }
        }
    }
}

@Composable
private fun SuggestionChip(text: String, onClick: () -> Unit, colors: SciFiTheme.SciFiColors) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(20.dp)).clickable { onClick() }.padding(4.dp),
        color = colors.primary.copy(alpha = 0.05f),
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.primary.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontSize = 11.sp, color = colors.primary)
    }
}

@Composable
private fun ChatBubble(message: ChatMessage, colors: SciFiTheme.SciFiColors) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val bgColor = if (message.isUser) colors.primary.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.05f)
    val borderColor = if (message.isUser) colors.primary.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.1f)
    
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(
                    topStart = 16.dp, topEnd = 16.dp,
                    bottomStart = if (message.isUser) 16.dp else 0.dp,
                    bottomEnd = if (message.isUser) 0.dp else 16.dp
                ))
                .background(bgColor)
                .border(1.dp, borderColor, RoundedCornerShape(
                    topStart = 16.dp, topEnd = 16.dp,
                    bottomStart = if (message.isUser) 16.dp else 0.dp,
                    bottomEnd = if (message.isUser) 0.dp else 16.dp
                ))
                .padding(12.dp)
        ) {
            if (message.isUser) {
                Text(message.text, fontSize = 14.sp, color = Color.White)
            } else {
                DecodingText(text = message.text, typingSpeed = 10L, style = TextStyle(fontSize = 14.sp, color = Color.White))
            }
        }
    }
}

@Composable
private fun TypingIndicator(colors: SciFiTheme.SciFiColors) {
    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = colors.accent, strokeWidth = 1.dp)
        Spacer(Modifier.width(8.dp))
        Text("AI IS COMPUTING...", fontSize = 10.sp, color = colors.accent)
    }
}

@Composable
private fun InputRow(text: String, onTextChange: (String) -> Unit, onSend: () -> Unit, colors: SciFiTheme.SciFiColors) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            .border(1.dp, colors.primary.copy(alpha = 0.2f), CircleShape)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Outlined.Mic, contentDescription = null, tint = colors.textSecondary)
        Spacer(Modifier.width(12.dp))
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
            cursorBrush = SolidColor(colors.primary),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onSend() }),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) Text("Type economic query...", color = Color.Gray, fontSize = 14.sp)
                innerTextField()
            }
        )
        IconButton(onClick = onSend) {
            Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Send", tint = colors.primary)
        }
    }
}
