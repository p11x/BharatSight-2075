package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.RetroDarkColors
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun TelemetryDrawer(
    selectedRoute: String = "overview",
    onNavigate: (String) -> Unit = {}
) {
    ModalDrawerSheet(
        drawerContainerColor = Color(0xFF050505),
        drawerContentColor = RetroDarkColors.TextPrimary,
        modifier = Modifier.width(320.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                text = "COMMAND CENTER // ALPHA",
                color = RetroDarkColors.NeonCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Categorized List


            // Spacer to push footer to bottom
            Spacer(modifier = Modifier.weight(1f))

            // Footer: SYSTEM DIAGNOSTICS and DiagnosticLedGrid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SYSTEM DIAGNOSTICS",
                    color = RetroDarkColors.NeonGreen,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
                
                DiagnosticLedGrid(
                    rows = 3,
                    columns = 3,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
