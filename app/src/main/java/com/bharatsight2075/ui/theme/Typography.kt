package com.bharatsight2075.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text

object StrictTypeScale {
    val HeroNumber = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.W700,
        fontFamily = FontFamily.Monospace,
        letterSpacing = (-0.5).sp
    )
    val MetricLabel = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.W500,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 0.15.em
    )
    val BodyMono = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.W400,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 0.02.em
    )
    val ChartCaption = TextStyle(
        fontSize = 9.sp,
        fontWeight = FontWeight.W400,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 0.08.em
    )
    val SectionHead = TextStyle(
        fontSize = 9.sp,
        fontWeight = FontWeight.W600,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 0.12.em
    )
}

/**
 * Text legibility guard that adds a semi-transparent backing pill for contrast.
 */
@Composable
fun ReadableText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier.drawBehind {
            drawRoundRect(
                color = Color.Black.copy(alpha = 0.35f),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
        }
    )
}

val RetroDarkTypography = Typography(
    displayLarge = StrictTypeScale.HeroNumber,
    bodyLarge = StrictTypeScale.BodyMono,
    labelSmall = StrictTypeScale.ChartCaption
    // Map other Material3 types as needed
)
