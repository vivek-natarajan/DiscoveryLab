package com.example.discoverylab.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * DiscoveryLab Typography System
 *
 * Font families are set to defaults - replace with custom fonts when available:
 * - GeistFontFamily -> FontFamily.SansSerif (placeholder)
 * - GelicaFontFamily -> FontFamily.Serif (placeholder)
 * - GeorgiaFontFamily -> FontFamily.Serif (placeholder)
 * - ShantellSansFontFamily -> FontFamily.Cursive (placeholder)
 * - BungeeFontFamily -> FontFamily.Monospace (placeholder)
 */

// Placeholder font families - replace with actual fonts when added to resources
val GeistFontFamily = FontFamily.SansSerif
val GelicaFontFamily = FontFamily.Serif
val GeorgiaFontFamily = FontFamily.Serif
val ShantellSansFontFamily = FontFamily.Cursive
val BungeeFontFamily = FontFamily.Monospace
val GeistMonoFontFamily = FontFamily.Monospace

// Extension functions for TextStyle
fun TextStyle.withColor(color: Color): TextStyle = copy(color = color)
fun TextStyle.withFontSize(fontSize: TextUnit): TextStyle = copy(fontSize = fontSize)
fun TextStyle.withFontFamily(fontFamily: FontFamily): TextStyle = copy(fontFamily = fontFamily)
fun TextStyle.withFontWeight(fontWeight: FontWeight): TextStyle = copy(fontWeight = fontWeight)
fun TextStyle.withFontStyle(fontStyle: FontStyle): TextStyle = copy(fontStyle = fontStyle)
fun TextStyle.withLetterSpacing(letterSpacing: TextUnit): TextStyle = copy(letterSpacing = letterSpacing)
fun TextStyle.withLineHeight(lineHeight: TextUnit): TextStyle = copy(lineHeight = lineHeight)
fun TextStyle.withTextAlign(textAlign: TextAlign): TextStyle = copy(textAlign = textAlign)

/**
 * Legacy Typography System (11 tokens)
 */
data class DiscoveryLabTypography(
    val labelSmall: TextStyle,
    val labelMedium: TextStyle,
    val labelLarge: TextStyle,
    val bodySmall: TextStyle,
    val bodyMedium: TextStyle,
    val bodyLarge: TextStyle,
    val titleSmall: TextStyle,
    val titleMedium: TextStyle,
    val titleLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineLarge: TextStyle
)

val Typography = DiscoveryLabTypography(
    labelSmall = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 27.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 25.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 26.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 44.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 50.sp,
        letterSpacing = (-0.02).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 56.sp,
        letterSpacing = (-0.02).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 72.sp,
        lineHeight = 107.sp,
    )
)

/**
 * Extended Typography System (26 tokens)
 */
data class DiscoveryLabTypographyExtended(
    // Headings (7 tokens)
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,

    // Body Text
    val bodySerifLargeReg: TextStyle,
    val bodySerifLargeBold: TextStyle,
    val scribble: TextStyle,
    val bodySerif: TextStyle,
    val bodyLongReg: TextStyle,
    val bodyLongMed: TextStyle,
    val bodyLongBold: TextStyle,
    val bodyShortReg: TextStyle,
    val bodyShortMed: TextStyle,
    val bodyShortSemi: TextStyle,
    val label: TextStyle,
    val labelMedium: TextStyle,
    val label1Reg: TextStyle,

    // Buttons, Numbers & Labels
    val buttonSerif: TextStyle,
    val buttonSmall: TextStyle,
    val numbersLarge: TextStyle,
    val labelMono: TextStyle,

    // Special Fonts
    val bungee: TextStyle
)

val ExtendedTypography = DiscoveryLabTypographyExtended(
    // Headings
    h1 = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 72.sp,
        lineHeight = 80.sp,
        letterSpacing = 0.sp
    ),
    h2 = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 44.sp,
        lineHeight = 56.sp,
        letterSpacing = 0.sp
    ),
    h3 = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    h5 = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // Body Text
    bodySerifLargeReg = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    bodySerifLargeBold = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    scribble = TextStyle(
        fontFamily = ShantellSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    bodySerif = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    bodyLongReg = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyLongMed = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyLongBold = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyShortReg = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyShortMed = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyShortSemi = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    label = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    label1Reg = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),

    // Buttons, Numbers & Labels
    buttonSerif = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.44.sp
    ),
    buttonSmall = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    numbersLarge = TextStyle(
        fontFamily = GelicaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 44.sp,
        lineHeight = 44.sp,
        letterSpacing = 2.sp
    ),
    labelMono = TextStyle(
        fontFamily = GeistMonoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    // Special Fonts
    bungee = TextStyle(
        fontFamily = BungeeFontFamily,
        fontWeight = FontWeight(400),
        fontSize = 28.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)

// Material 3 Typography for MaterialTheme
val materialTypography = Typography(
    labelSmall = Typography.labelSmall,
    labelMedium = Typography.labelMedium,
    labelLarge = Typography.labelLarge,
    bodySmall = Typography.bodySmall,
    bodyMedium = Typography.bodyMedium,
    bodyLarge = Typography.bodyLarge,
    titleSmall = Typography.titleSmall,
    titleMedium = Typography.titleMedium,
    titleLarge = Typography.titleLarge,
    headlineMedium = Typography.headlineMedium
)
