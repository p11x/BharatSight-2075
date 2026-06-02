package com.bharatsight2075.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object SciFiTheme {
    // Theme enum for type safety
    enum class Theme {
        Cyberpunk,
        Hologram
    }

    // CompositionLocal for theme propagation
    val LocalSciFiTheme = staticCompositionLocalOf { Theme.Cyberpunk }

    val current: Theme
        @Composable
        get() = LocalSciFiTheme.current

    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val extendedColors: SciFiColors
        @Composable
        get() = if (current == Theme.Cyberpunk) CyberpunkSciFiColors else HologramSciFiColors

    val typography: StrictTypeScale
        get() = StrictTypeScale

    interface SciFiColors {
        val primary: Color
        val accent: Color
        val background: Color
        val surface: Color
        val gridLine: Color
        val positive: Color
        val negative: Color
        val textPrimary: Color
        val textSecondary: Color
        val textDisabled: Color
        val barPalette: List<Color>
    }

    private object CyberpunkSciFiColors : SciFiColors {
        override val primary = Color(0xFF00F5FF)
        override val accent = Color(0xFFFF6B35)
        override val background = Color(0xA10A0A1A)
        override val surface = Color(0xFF0A0A1A)
        override val gridLine = Color(0xFF1A1A1A)
        override val positive = Color(0xFF00E676)
        override val negative = Color(0xFFFF3B30)
        override val textPrimary = Color(0xFFE0E0E0)
        override val textSecondary = Color(0xFF808080)
        override val textDisabled = Color(0xFF404040)
        override val barPalette = listOf(Color(0xFF00F5FF), Color(0xFFFF6B35), Color(0xFF7B2FBE), Color(0xFF00E676), Color(0xFFFFD600))
    }

    private object HologramSciFiColors : SciFiColors {
        override val primary = Color(0xFF4FC3F7)
        override val accent = Color(0xB39DDBFF)
        override val background = Color(0xA1020B18)
        override val surface = Color(0xFF020B18)
        override val gridLine = Color(0xFF002240)
        override val positive = Color(0xFF00E676)
        override val negative = Color(0xFFFF3B30)
        override val textPrimary = Color(0xFFE0F8FF)
        override val textSecondary = Color(0xFF80B3BF)
        override val textDisabled = Color(0xFF406070)
        override val barPalette = listOf(Color(0xFF4FC3F7), Color(0xFFB39DDB), Color(0xFF80CBC4), Color(0xFFF48FB1), Color(0xFFFFCC02))
    }

    // Cyberpunk Color Palette (Current)
    object CyberpunkColors {
        val Black = Color(0xFF000000)
        val BlackElevated = Color(0xFF0A0A0A)
        val GridLine = Color(0xFF1A1A1A)
        
        val NeonOrange = Color(0xFFFF9500)
        val NeonYellow = Color(0xFFFFFF00)
        val NeonGold = Color(0xFFFFB300)
        val NeonCyan = Color(0xFF00E5FF)
        val NeonMagenta = Color(0xFFFF007F)
        val NeonGreen = Color(0xFF39FF14)
        
        val TextPrimary = Color(0xFFE0E0E0)
        val TextSecondary = Color(0xFF808080)
        val TextDisabled = Color(0xFF404040)
    }

    // Hologram Color Palette (New)
    object HologramColors {
        val DeepNavy = Color(0xFF00112D)
        val DeepNavyElevated = Color(0xFF001A3A)
        val GridLine = Color(0xFF002240)
        
        val IceBlue = Color(0xFF00F5FF)
        val BrightWhite = Color(0xFFFFFFFF)
        val EmeraldGreen = Color(0xFF50FF87)
        val ElectricPurple = Color(0xFFBF00FF)
        
        val TextPrimary = Color(0xFFE0F8FF)
        val TextSecondary = Color(0xFF80B3BF)
        val TextDisabled = Color(0xFF406070)
    }

    @Composable
    fun ProvideSciFiTheme(
        theme: Theme = Theme.Cyberpunk,
        content: @Composable () -> Unit
    ) {
        CompositionLocalProvider(LocalSciFiTheme provides theme) {
            when (theme) {
                Theme.Cyberpunk -> ProvideCyberpunkTheme(content)
                Theme.Hologram -> ProvideHologramTheme(content)
            }
        }
    }

    @Composable
    private fun ProvideCyberpunkTheme(content: @Composable () -> Unit) {
        val cyberpunkColorScheme = darkColorScheme(
            primary = CyberpunkSciFiColors.primary,
            secondary = CyberpunkSciFiColors.accent,
            tertiary = CyberpunkSciFiColors.positive,
            background = CyberpunkSciFiColors.background,
            surface = CyberpunkSciFiColors.surface,
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onTertiary = Color.Black,
            onBackground = CyberpunkSciFiColors.textPrimary,
            onSurface = CyberpunkSciFiColors.textPrimary
        )
        
        RetroDarkThemeWithColors(content, cyberpunkColorScheme)
    }

    @Composable
    private fun ProvideHologramTheme(content: @Composable () -> Unit) {
        val hologramColorScheme = darkColorScheme(
            primary = HologramSciFiColors.primary,
            secondary = HologramSciFiColors.accent,
            tertiary = HologramSciFiColors.positive,
            background = HologramSciFiColors.background,
            surface = HologramSciFiColors.surface,
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onTertiary = Color.Black,
            onBackground = HologramSciFiColors.textPrimary,
            onSurface = HologramSciFiColors.textPrimary
        )
        
        RetroDarkThemeWithColors(content, hologramColorScheme)
    }

    // Helper to apply a color scheme to the existing theme structure
    @Composable
    private fun RetroDarkThemeWithColors(
        content: @Composable () -> Unit,
        colorScheme: ColorScheme
    ) {
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
}