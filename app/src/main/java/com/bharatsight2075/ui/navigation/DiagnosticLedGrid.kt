package com.bharatsight2075.ui.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun DiagnosticLedGrid(
    rows: Int = 3,
    columns: Int = 3,
    modifier: Modifier = Modifier
) {
    // Create a mutable state for LED states
    val ledStates: MutableState<BooleanArray> = remember {
        mutableStateOf(BooleanArray(rows * columns) { Random.nextBoolean() })
    }
    
    // LaunchedEffect to randomly toggle LEDs
    LaunchedEffect(Unit) {
        while (true) {
            delay(((500..1500).random()).toLong()) // Random delay between 0.5-1.5 seconds
            val index = Random.nextInt(ledStates.value.size)
            ledStates.value[index] = !ledStates.value[index]
        }
    }
    
     Canvas(
         modifier = modifier
             .width(((rows * 4 + (rows - 1) * 2).dp))
             .height(((columns * 4 + (columns - 1) * 2).dp))
      ) {
          val dotSize = 4.dp
          val spacing = 2.dp
          
           for (row in 0 until rows) {
               for (col in 0 until columns) {
                   val index = row * columns + col
                   val isOn = ledStates.value[index]
                   val x = (col * (dotSize + spacing).value + (dotSize/2).value)
                   val y = (row * (dotSize + spacing).value + (dotSize/2).value)
                   
                   drawCircle(
                       color = if (isOn) RetroDarkColors.NeonGreen else RetroDarkColors.GridLine,
                       center = Offset(x, y),
                       radius = (dotSize/2).value
                   )
               }
           }
      }
}