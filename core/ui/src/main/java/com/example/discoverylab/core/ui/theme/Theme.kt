package com.example.discoverylab.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * DiscoveryLab Theme System
 *
 * Usage:
 * ```
 * DiscoveryLabTheme {
 *     // Access colors
 *     val primaryText = DiscoveryLabTheme.colors.text.primary
 *     val bgColor = DiscoveryLabTheme.colors.background.bgSurface
 *
 *     // Access typography
 *     val headlineStyle = DiscoveryLabTheme.style.headlineLarge
 *     val h1Style = DiscoveryLabTheme.typography.h1
 * }
 * ```
 */

private val LocalDiscoveryLabColorScheme = staticCompositionLocalOf<ColorScheme> {
    error("No ColorScheme provided")
}

private val LocalDiscoveryLabTypography = staticCompositionLocalOf<DiscoveryLabTypography> {
    error("No DiscoveryLabTypography provided")
}

private val LocalDiscoveryLabTypographyExtended = staticCompositionLocalOf<DiscoveryLabTypographyExtended> {
    error("No DiscoveryLabTypographyExtended provided")
}

object DiscoveryLabTheme {
    val colors: ColorScheme
        @Composable
        get() = LocalDiscoveryLabColorScheme.current

    val style: DiscoveryLabTypography
        @Composable
        get() = LocalDiscoveryLabTypography.current

    val typography: DiscoveryLabTypographyExtended
        @Composable
        get() = LocalDiscoveryLabTypographyExtended.current
}

/**
 * Color scheme data class for theme-specific colors
 * Provides access to all color scales and semantic tokens
 */
data class ColorScheme(
    val core: CoreColors = CoreColors,
    val semantic: SemanticColors = SemanticColors,
    val neutral: Neutral = Neutral,
    val blue: Blue = Blue,
    val green: Green = Green,
    val red: Red = Red,
    val orange: Orange = Orange,
    val yellow: Yellow = Yellow,
    val purple: Purple = Purple,
    val pink: Pink = Pink,
    val coral: Coral = Coral,
    val azure: Azure = Azure,
    val neon: Neon = Neon,
    // Semantic token objects for design system
    val text: TextTokens = TextTokens,
    val icon: IconTokens = IconTokens,
    val border: BorderTokens = BorderTokens,
    val cta: CTATokens = CTATokens,
    val background: BackgroundTokens = BackgroundTokens
)

// Default schemes
val lightColorScheme = ColorScheme()
val darkColorScheme = ColorScheme()

@Composable
fun DiscoveryLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.core.Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalDiscoveryLabColorScheme provides colorScheme,
        LocalDiscoveryLabTypography provides Typography,
        LocalDiscoveryLabTypographyExtended provides ExtendedTypography
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) materialDarkColorScheme else materialLightColorScheme,
            typography = materialTypography
        ) {
            content()
        }
    }
}
