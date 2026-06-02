package com.bharatsight2075.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun ChartTooltip(
    label: String,
    value: String,
    delta: String? = null,
    source: String = "RBI / NSE",
    onDismiss: () -> Unit
) {
    Popup(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(Color(0xFF0E0E1A).copy(alpha = 0.95f), RoundedCornerShape(8.dp))
                .border(1.dp, SciFiTheme.extendedColors.primary, RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Column {
                Text(label.uppercase(), fontSize = 10.sp, color = Color.White.copy(alpha = 0.6f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SciFiTheme.extendedColors.primary)
                    if (delta != null) {
                        Spacer(Modifier.width(8.dp))
                        Text(delta, fontSize = 11.sp, color = if (delta.contains("▲")) Color(0xFF00E676) else Color(0xFFFF5252))
                    }
                }
                Text(source, fontSize = 9.sp, color = Color.White.copy(alpha = 0.4f))
            }
        }
    }
}
