package com.bharatsight2075.ui.components

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartFullscreenModal(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable BoxScope.(Modifier) -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF080810),
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title.uppercase(),
                    style = SciFiTheme.typography.BodyMono,
                    color = SciFiTheme.extendedColors.primary
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            // Interactive Viewport
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                val interactiveModifier = Modifier
                    .graphicsLayer(
                        scaleX = scale.coerceIn(0.5f, 5f),
                        scaleY = scale.coerceIn(0.5f, 5f),
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .transformable(state = state)
                
                content(interactiveModifier)
            }
            
            // Interaction Hint
            Text(
                text = "PINCH TO ZOOM · DRAG TO PAN",
                style = SciFiTheme.typography.ChartCaption,
                color = Color.White.copy(alpha = 0.4f),
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            )
        }
    }
}
