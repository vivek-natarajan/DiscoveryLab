package com.example.discoverylab.content.solarsystem.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Color palette for the Solar System module - dark theme.
 */
object SolarColors {
    val background = Color(0xFF0A0A0A)
    val cardBackground = Color(0xFF121212)
    val textPrimary = Color(0xFFE8E8E8)
    val textSecondary = Color(0xFF8A8A8A)
    val divider = Color(0xFF2A2A2A)
    val accent = Color(0xFFB0B0B0)

    // Planet accent colors
    val sun = Color(0xFFFF6B00)
    val mercury = Color(0xFF9CA3AF)
    val venus = Color(0xFFD97706)
    val earth = Color(0xFF3B82F6)
    val moon = Color(0xFF9CA3AF)
    val mars = Color(0xFFDC2626)
    val jupiter = Color(0xFFF59E0B)
    val saturn = Color(0xFFD4A574)
    val uranus = Color(0xFF67E8F9)
    val neptune = Color(0xFF3B82F6)

    fun forBody(id: String): Color = when (id.lowercase()) {
        "sun" -> sun
        "mercury" -> mercury
        "venus" -> venus
        "earth" -> earth
        "moon" -> moon
        "mars" -> mars
        "jupiter" -> jupiter
        "saturn" -> saturn
        "uranus" -> uranus
        "neptune" -> neptune
        else -> textSecondary
    }
}
