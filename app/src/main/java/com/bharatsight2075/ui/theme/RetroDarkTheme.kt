package com.bharatsight2075.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun RetroDarkTheme(
    content: @Composable () -> Unit
) {
    val currentTheme = SciFiTheme.LocalSciFiTheme.current
    val colorScheme = when (currentTheme) {
        SciFiTheme.Theme.Cyberpunk -> darkColorScheme(
            primary = SciFiTheme.CyberpunkColors.NeonOrange,
            secondary = SciFiTheme.CyberpunkColors.NeonCyan,
            tertiary = SciFiTheme.CyberpunkColors.NeonMagenta,
            background = SciFiTheme.CyberpunkColors.Black,
            surface = SciFiTheme.CyberpunkColors.BlackElevated,
            onPrimary = SciFiTheme.CyberpunkColors.Black,
            onSecondary = SciFiTheme.CyberpunkColors.Black,
            onTertiary = SciFiTheme.CyberpunkColors.Black,
            onBackground = SciFiTheme.CyberpunkColors.TextPrimary,
            onSurface = SciFiTheme.CyberpunkColors.TextPrimary
        )
        SciFiTheme.Theme.Hologram -> darkColorScheme(
            primary = SciFiTheme.HologramColors.IceBlue,
            secondary = SciFiTheme.HologramColors.BrightWhite,
            tertiary = SciFiTheme.HologramColors.EmeraldGreen,
            background = SciFiTheme.HologramColors.DeepNavy,
            surface = SciFiTheme.HologramColors.DeepNavyElevated,
            onPrimary = SciFiTheme.HologramColors.DeepNavy,
            onSecondary = SciFiTheme.HologramColors.DeepNavy,
            onTertiary = SciFiTheme.HologramColors.DeepNavy,
            onBackground = SciFiTheme.HologramColors.TextPrimary,
            onSurface = SciFiTheme.HologramColors.TextPrimary
        )
        else -> darkColorScheme(
            primary = SciFiTheme.CyberpunkColors.NeonOrange,
            secondary = SciFiTheme.CyberpunkColors.NeonCyan,
            tertiary = SciFiTheme.CyberpunkColors.NeonMagenta,
            background = SciFiTheme.CyberpunkColors.Black,
            surface = SciFiTheme.CyberpunkColors.BlackElevated,
            onPrimary = SciFiTheme.CyberpunkColors.Black,
            onSecondary = SciFiTheme.CyberpunkColors.Black,
            onTertiary = SciFiTheme.CyberpunkColors.Black,
            onBackground = SciFiTheme.CyberpunkColors.TextPrimary,
            onSurface = SciFiTheme.CyberpunkColors.TextPrimary
        )
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = RetroDarkTypography,
        content = {
            GridBackgroundSurface {
                content()
            }
        }
    )
}