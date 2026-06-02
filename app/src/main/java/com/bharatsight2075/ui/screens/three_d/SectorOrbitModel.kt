package com.bharatsight2075.ui.screens.three_d

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin

@Immutable
data class EconomicSector(
    val name: String,
    val marketCap: Float, // Size
    val growthRate: Float, // Orbit speed
    val momentum: Color // Color
)

@Composable
fun SectorOrbitOrrery(
    modifier: Modifier = Modifier,
    sectors: List<EconomicSector> = listOf(
        EconomicSector("FINANCE", 0.8f, 1.2f, RetroDarkColors.NeonCyan),
        EconomicSector("IT", 0.9f, 2.0f, RetroDarkColors.NeonGreen),
        EconomicSector("AGRI", 0.4f, 0.5f, RetroDarkColors.NeonOrange),
        EconomicSector("MFG", 0.7f, 1.0f, RetroDarkColors.NeonMagenta),
        EconomicSector("ENERGY", 0.6f, 0.8f, Color.Yellow)
    )
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Orbit")
    
    val sectorAnimations = sectors.map { sector ->
        val rotationSpeed = 5000 / sector.growthRate
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(rotationSpeed.toInt(), easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = sector.name
        )
    }

    Box(modifier = modifier.background(Color.Black)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            
            // Central Rupee Sun
            drawCircle(
                color = RetroDarkColors.NeonCyan,
                radius = 40f,
                center = center,
                style = Stroke(width = 4f)
            )
            
            sectors.forEachIndexed { index, sector ->
                val orbitRadius = 100f + (index * 60f)
                val angle = sectorAnimations[index].value
                
                val x = center.x + orbitRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
                val y = center.y + orbitRadius * sin(Math.toRadians(angle.toDouble())).toFloat() * 0.5f // Elliptical
                
                // Draw Orbit Path
                drawOval(
                    color = Color.Gray.copy(alpha = 0.2f),
                    topLeft = Offset(center.x - orbitRadius, center.y - orbitRadius * 0.5f),
                    size = androidx.compose.ui.geometry.Size(orbitRadius * 2, orbitRadius),
                    style = Stroke(width = 1f)
                )
                
                // Draw Sector Planet
                drawCircle(
                    color = sector.momentum,
                    radius = 10f + (sector.marketCap * 20f),
                    center = Offset(x, y)
                )
            }
        }
        
        Column(modifier = Modifier.align(Alignment.TopStart).padding(16.dp)) {
            Text("ECONOMIC ORRERY SYSTEM", color = RetroDarkColors.TextSecondary, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
        }
    }
}
