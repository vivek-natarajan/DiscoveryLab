package com.example.discoverylab.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * DiscoveryLab Color System
 *
 * This file defines the complete color palette for the DiscoveryLab design system.
 * Colors are organized into semantic categories and can be accessed through DiscoveryLabTheme.colors
 *
 * USAGE EXAMPLES:
 *
 * // Access individual color palettes
 * DiscoveryLabTheme.colors.blue.B500          // Primary blue
 * DiscoveryLabTheme.colors.neutral.N400       // Mid-tone neutral
 * DiscoveryLabTheme.colors.purple.PU600       // Primary purple
 *
 * // Use semantic tokens (recommended for consistent theming)
 * DiscoveryLabTheme.colors.text.primary       // Black text
 * DiscoveryLabTheme.colors.text.secondary     // Gray text
 * DiscoveryLabTheme.colors.background.bgSurface // App background (#FFFBF7)
 * DiscoveryLabTheme.colors.icon.brand         // Orange brand icon
 * DiscoveryLabTheme.colors.border.secondary   // Light gray border
 */

// Core palette - foundational colors
object CoreColors {
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF1A1A1A)
}

// Semantic color tokens - purpose-based naming
object SemanticColors {
    val Primary = Blue
    val Secondary = Neutral
    val Error = Red
    val Warning = Orange
    val Success = Green
    val Info = Blue
}

// Color scales - organized in separate objects for better maintenance
object Neutral {
    val N50 = Color(0xFFFAFAFA)
    val N100 = Color(0xFFF5F5F5)
    val N200 = Color(0xFFE5E5E5)
    val N300 = Color(0xFFD4D4D4)
    val N400 = Color(0xFFA3A3A3)
    val N500 = Color(0xFF737373)
    val N600 = Color(0xFF525252)
    val N700 = Color(0xFF404040)
    val N800 = Color(0xFF262626)
    val N900 = Color(0xFF171717)
    val N950 = Color(0xFF0A0A0A)
    val N_gold = Color(0xFFB8860B)
}

object Blue {
    val B50 = Color(0xFFEFF6FF)
    val B100 = Color(0xFFDBEAFE)
    val B200 = Color(0xFFBFDBFE)
    val B300 = Color(0xFF93C5FD)
    val B400 = Color(0xFF60A5FA)
    val B500 = Color(0xFF3B82F6)
    val B600 = Color(0xFF2563EB)
    val B700 = Color(0xFF1D4ED8)
    val B800 = Color(0xFF1E40AF)
    val B900 = Color(0xFF1E3A8A)
    val B950 = Color(0xFF172554)
}

object Green {
    val G50 = Color(0xFFF0FDF4)
    val G100 = Color(0xFFDCFCE7)
    val G200 = Color(0xFFBBF7D0)
    val G300 = Color(0xFF86EFAC)
    val G400 = Color(0xFF4ADE80)
    val G500 = Color(0xFF22C55E)
    val G600 = Color(0xFF16A34A)
    val G700 = Color(0xFF15803D)
    val G800 = Color(0xFF166534)
    val G900 = Color(0xFF14532D)
    val G950 = Color(0xFF052E16)
}

object Red {
    val R50 = Color(0xFFFEF2F2)
    val R100 = Color(0xFFFEE2E2)
    val R200 = Color(0xFFFECACA)
    val R300 = Color(0xFFFCA5A5)
    val R400 = Color(0xFFF87171)
    val R500 = Color(0xFFEF4444)
    val R600 = Color(0xFFDC2626)
    val R700 = Color(0xFFB91C1C)
    val R800 = Color(0xFF991B1B)
    val R900 = Color(0xFF7F1D1D)
    val R950 = Color(0xFF450A0A)
}

object Orange {
    val O50 = Color(0xFFFFF7ED)
    val O100 = Color(0xFFFFEDD5)
    val O200 = Color(0xFFFED7AA)
    val O300 = Color(0xFFFDBA74)
    val O400 = Color(0xFFFB923C)
    val O500 = Color(0xFFF56F1E)
    val O600 = Color(0xFFEA580C)
    val O700 = Color(0xFFC2410C)
    val O800 = Color(0xFF9A3412)
    val O900 = Color(0xFF7C2D12)
    val O950 = Color(0xFF431407)
    val O_brown = Color(0xFF9C3700)
    val O_orange = Color(0xFFFF6F1E)
}

object Yellow {
    val Y50 = Color(0xFFFFFBEB)
    val Y100 = Color(0xFFFEF3C7)
    val Y200 = Color(0xFFFDE68A)
    val Y300 = Color(0xFFFCD34D)
    val Y400 = Color(0xFFFBBF24)
    val Y500 = Color(0xFFF59E0B)
    val Y600 = Color(0xFFD97706)
    val Y700 = Color(0xFFB45309)
    val Y800 = Color(0xFF92400E)
    val Y900 = Color(0xFF78350F)
    val Y950 = Color(0xFF451A03)
    val Y_gold = Color(0xFFFFC72C)
}

object Pink {
    val P50 = Color(0xFFFFF7FD)
    val P100 = Color(0xFFFFF2FB)
    val P200 = Color(0xFFFFEDF9)
    val P300 = Color(0xFFFFE6F7)
    val P400 = Color(0xFFFFDEF5)
    val P500 = Color(0xFFFFCCEF)
    val P600 = Color(0xFFFFB3E7)
    val P700 = Color(0xFFFF99DF)
    val P800 = Color(0xFFFF66CF)
    val P900 = Color(0xFFFF4DC6)
    val P950 = Color(0xFFFF1AB6)
}

object Coral {
    val C50 = Color(0xFFFFF4EE)
    val C100 = Color(0xFFFFEEE6)
    val C200 = Color(0xFFFFDECC)
    val C300 = Color(0xFFFFCDB3)
    val C400 = Color(0xFFFFBD99)
    val C500 = Color(0xFFFF9E6B)
    val C600 = Color(0xFFFF8B4D)
    val C700 = Color(0xFFFF7A33)
    val C800 = Color(0xFFFF6A1A)
    val C900 = Color(0xFFFF5900)
    val C950 = Color(0xFFCC4700)
}

object Azure {
    val A50 = Color(0xFFE8F2FD)
    val A100 = Color(0xFFD0E5FB)
    val A200 = Color(0xFFA2CBF6)
    val A300 = Color(0xFF73B0F2)
    val A400 = Color(0xFF4496EE)
    val A500 = Color(0xFF167EEA)
    val A600 = Color(0xFF1470D2)
    val A700 = Color(0xFF0F57A3)
    val A800 = Color(0xFF0B3E75)
    val A900 = Color(0xFF09325D)
    val A950 = Color(0xFF072546)
}

object Neon {
    val NE50 = Color(0xFFFCFEE7)
    val NE100 = Color(0xFFF5FCB6)
    val NE200 = Color(0xFFEEF985)
    val NE300 = Color(0xFFEAF86D)
    val NE400 = Color(0xFFE7F755)
    val NE500 = Color(0xFFE1F531)
    val NE600 = Color(0xFFDCF40B)
    val NE700 = Color(0xFFC6DB0A)
    val NE800 = Color(0xFFB0C309)
    val NE900 = Color(0xFF9AAA08)
    val NE950 = Color(0xFF6E7A06)
}

object Purple {
    val PU50 = Color(0xFFECECF9)
    val PU100 = Color(0xFFC7C5EC)
    val PU200 = Color(0xFFA29FE0)
    val PU300 = Color(0xFF7D78D3)
    val PU400 = Color(0xFF6A65CD)
    val PU500 = Color(0xFF453EC1)
    val PU600 = Color(0xFF3E38AD)
    val PU700 = Color(0xFF302C87)
    val PU800 = Color(0xFF292574)
    val PU900 = Color(0xFF221F60)
    val PU950 = Color(0xFF1C194D)
}

// Semantic Token Objects - Design System Implementation
object TextTokens {
    val primary = CoreColors.Black
    val primaryInverse = CoreColors.White
    val secondary = Neutral.N400
    val secondaryVariant = Neutral.N500
    val highlight = Orange.O500
    val critical = Red.R600
    val tertiary = Neutral.N500
}

object IconTokens {
    val brand = Orange.O500
    val primary = CoreColors.Black
    val primaryInverse = CoreColors.White
    val success = Green.G600
    val critical = Red.R600
    val caution = Yellow.Y400
}

object BorderTokens {
    val primary = CoreColors.Black
    val primaryInverse = CoreColors.White
    val secondary = Color(0xFFDCD8D3)
    val tertiary = Neutral.N300
}

object CTATokens {
    val primaryActive = CoreColors.Black
    val primaryPressed = Neutral.N700
    val secondaryActive = CoreColors.White
    val secondaryPressed = Neutral.N100
    val primaryShadow = Neutral.N600
}

object BackgroundTokens {
    val bgSurface = Color(0xFFFFFBF7)
    val bgSurfaceInverse = CoreColors.Black
    val containerPrimary = Color(0xFFFFFEFC)
    val containerPrimaryInverse = CoreColors.White
    val disabled = Neutral.N200
    val disabledSecondary = Neutral.N300
    val disabledTertiary = Neutral.N400
}

// Theme color schemes
val materialDarkColorScheme = darkColorScheme(
    primary = CoreColors.White,
    onPrimary = CoreColors.Black,
    secondary = Neutral.N100,
    onSecondary = Neutral.N800,
    tertiary = CoreColors.White,
    onTertiary = Neutral.N600,
    background = CoreColors.Black,
    onBackground = CoreColors.White,
    surface = CoreColors.Black,
    onSurface = CoreColors.White
)

val materialLightColorScheme = lightColorScheme(
    primary = CoreColors.Black,
    onPrimary = CoreColors.White,
    secondary = Neutral.N800,
    onSecondary = Neutral.N100,
    tertiary = Neutral.N600,
    onTertiary = CoreColors.White,
    background = Color(0xFFFFFBF7),
    onBackground = CoreColors.Black,
    surface = Color(0xFFFFFBF7),
    onSurface = CoreColors.Black
)
