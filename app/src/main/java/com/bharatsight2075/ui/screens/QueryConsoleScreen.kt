package com.bharatsight2075.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.microinteraction.DecodingText
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.viewmodel.ChatMessage
import com.bharatsight2075.viewmodel.EconomyQueryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueryConsoleScreen(
    viewModel: EconomyQueryViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    var textInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "AI Query Engine",
                    badge = "GEMINI NANO",
                    badgeColor = Color(0xFF7C4DFF),
                    onBackClick = onBack
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
                .padding(16.dp)
        ) {
            DecodingText(
                text = "TERMINAL_PROMPT v2.075",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }
                if (isTyping) {
                    item {
                        Text(
                            "ANALYZING...",
                            color = SciFiTheme.extendedColors.primary,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = SciFiTheme.extendedColors.primary
                ),
                placeholder = {
                    Text(
                        "ENTER QUERY...",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        color = SciFiTheme.extendedColors.textSecondary
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SciFiTheme.extendedColors.primary,
                    unfocusedBorderColor = SciFiTheme.extendedColors.textSecondary,
                    focusedTextColor = SciFiTheme.extendedColors.primary,
                    unfocusedTextColor = SciFiTheme.extendedColors.primary
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    viewModel.sendQuery(textInput)
                    textInput = ""
                }),
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.sendQuery(textInput)
                        textInput = ""
                    }) {
                        Text("GO", color = SciFiTheme.extendedColors.primary, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                }
            )
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (message.isUser) SciFiTheme.extendedColors.primary.copy(alpha = 0.1f) else Color.DarkGray.copy(alpha = 0.3f),
            shape = RoundedCornerShape(4.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp, 
                if (message.isError) Color.Red else if (message.isUser) SciFiTheme.extendedColors.primary else SciFiTheme.extendedColors.textSecondary
            )
        ) {
            Text(
                text = (if (message.isUser) "> " else ">> ") + message.text,
                modifier = Modifier.padding(8.dp),
                color = if (message.isError) Color.Red else Color.White,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
