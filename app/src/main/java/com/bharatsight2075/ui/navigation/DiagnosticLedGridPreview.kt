package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import com.bharatsight2075.ui.theme.RetroDarkColors
import androidx.compose.material3.Text

@Preview(showBackground = true, widthDp = 100, heightDp = 100)
@Composable
fun DiagnosticLedGridPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "DiagnosticLedGrid Test",
            color = RetroDarkColors.TextPrimary
        )
        
         DiagnosticLedGrid(
             rows = 3,
             columns = 3,
             modifier = Modifier
                 .width(48.dp)
                 .height(48.dp)
         )
        
        Spacer(modifier = Modifier.height(16.dp))
        
         DiagnosticLedGrid(
             rows = 4,
             columns = 4,
             modifier = Modifier
                 .width(64.dp)
                 .height(64.dp)
         )
    }
}