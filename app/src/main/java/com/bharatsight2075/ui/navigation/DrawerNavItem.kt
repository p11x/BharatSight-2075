package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.RetroDarkColors
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale

@Composable
fun DrawerNavItem(
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemColor = if (isSelected) Color(0xFF0044CC) else Color.Transparent
    val textColor = if (isSelected) Color.White else RetroDarkColors.TextPrimary
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(itemColor)
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon placeholder (would be replaced with actual icons)
        Canvas(modifier = Modifier.size(24.dp)) {
            // Simple geometric icon based on category - in real app, use VectorAsset
            drawCircle(
                color = if (isSelected) Color.White else Color.Gray,
                center = Offset(size.width / 2, size.height / 2),
                radius = 4.dp.toPx()
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = title,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}