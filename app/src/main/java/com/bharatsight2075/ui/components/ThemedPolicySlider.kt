package com.bharatsight2075.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.Dimensions
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun ThemedPolicySlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = spring(stiffness = 300f),
        label = "SliderFill"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = SciFiTheme.typography.BodyMono, color = extendedColors.textSecondary)
            Text("${(value * 100).toInt()}%", style = SciFiTheme.typography.BodyMono, color = extendedColors.primary)
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        Box(contentAlignment = Alignment.CenterStart) {
            // Track Background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(extendedColors.primary.copy(alpha = 0.25f), CircleShape)
            )
            
            // Invisible slider for interaction
            Slider(
                value = value,
                onValueChange = onValueChange,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Transparent,
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Track Active Fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedValue)
                    .height(3.dp)
                    .background(extendedColors.primary, CircleShape)
            )

            // Custom Thumb
            Box(
                modifier = Modifier
                    .offset(x = (animatedValue * 300).dp) // Note: Simplified, ideally use onGloballyPositioned
                    .size(18.dp)
                    .drawBehind {
                        drawCircle(extendedColors.primary.copy(alpha = 0.3f), radius = 14.dp.toPx())
                    }
                    .border(2.dp, Color.White, CircleShape)
                    .background(extendedColors.primary, CircleShape)
            )
        }
    }
}
