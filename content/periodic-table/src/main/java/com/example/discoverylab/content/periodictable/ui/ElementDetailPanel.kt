package com.example.discoverylab.content.periodictable.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.periodictable.data.Element

// ============================================
// COLOR PALETTE (Muted Sage Green Theme)
// ============================================

private object DetailColors {
    val background = Color.White            // White background
    val surface = Color.White
    val textPrimary = Color(0xFF2A2A2A)     // Dark gray
    val textSecondary = Color(0xFF6A6A6A)   // Medium gray
    val divider = Color(0xFFE0E0E0)         // Light gray divider
    val accent = Color(0xFF4A4A4A)          // For links
}

/**
 * Element detail panel with single scrollable page.
 * Monospaced typography, technical/scientific aesthetic.
 * Reduced width, always visible on right side.
 */
@Composable
fun ElementDetailPanel(
    element: Element,
    onClose: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    hasPrevious: Boolean,
    hasNext: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp),
        color = DetailColors.background,
        shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                // ===== HEADER =====
                ElementHeader(element)

                Spacer(modifier = Modifier.height(16.dp))

                // ===== DESCRIPTION =====
                element.summary?.let { summary ->
                    Text(
                        text = "\"$summary\"",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = DetailColors.textPrimary,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                SectionDivider()

                // ===== PHYSICAL PROPERTIES =====
                SectionHeader("PHYSICAL PROPERTIES")
                Spacer(modifier = Modifier.height(10.dp))

                PropertyRow("ATOMIC MASS", "${String.format("%.3f", element.atomicMass)} u")
                element.density?.let { PropertyRow("DENSITY", "$it g/cm³") }
                element.meltingPoint?.let { PropertyRow("MELTING POINT", "$it K") }
                element.boilingPoint?.let { PropertyRow("BOILING POINT", "$it K") }
                PropertyRow("PHASE AT STP", element.naturalState.displayName)

                SectionDivider()

                // ===== ATOMIC PROFILE =====
                SectionHeader("ATOMIC PROFILE")
                Spacer(modifier = Modifier.height(10.dp))

                PropertyRow("PROTONS", element.protons.toString())
                PropertyRow("NEUTRONS", element.neutrons.toString())
                PropertyRow("ELECTRONS", element.electrons.toString())
                PropertyRow("VALENCE ELECTRONS", element.valenceElectrons.toString())
                if (element.electronConfigurationSemantic.isNotEmpty()) {
                    PropertyRow("ELECTRON CONFIGURATION", element.electronConfigurationSemantic)
                } else if (element.electronConfiguration.isNotEmpty()) {
                    PropertyRow("ELECTRON CONFIGURATION", element.electronConfiguration)
                }
                element.electronegativity?.let { PropertyRow("ELECTRONEGATIVITY", it.toString()) }
                element.electronAffinity?.let { PropertyRow("ELECTRON AFFINITY", "$it kJ/mol") }

                SectionDivider()

                // ===== DISCOVERY =====
                SectionHeader("DISCOVERY")
                Spacer(modifier = Modifier.height(10.dp))

                element.discoveredBy?.let { PropertyRow("DISCOVERED BY", it) }
                element.namedBy?.let { PropertyRow("NAMED BY", it) }
                element.discoveryYear?.let { PropertyRow("DISCOVERY YEAR", it.toString()) }
                element.nameOrigin?.let { PropertyRow("NAME ORIGIN", it) }

                // ===== ATOMIC RADII =====
                if (element.atomicRadiusEmpirical != null || element.covalentRadius != null || element.vanDerWaalsRadius != null) {
                    SectionDivider()
                    SectionHeader("ATOMIC RADII")
                    Spacer(modifier = Modifier.height(10.dp))

                    element.atomicRadiusEmpirical?.let { PropertyRow("ATOMIC RADIUS", "$it pm") }
                    element.covalentRadius?.let { PropertyRow("COVALENT RADIUS", "$it pm") }
                    element.vanDerWaalsRadius?.let { PropertyRow("VAN DER WAALS", "$it pm") }
                }

                // ===== CHEMICAL PROPERTIES =====
                if (element.oxidationStates.isNotEmpty() || element.standardElectrodePotential != null) {
                    SectionDivider()
                    SectionHeader("CHEMICAL PROPERTIES")
                    Spacer(modifier = Modifier.height(10.dp))

                    if (element.oxidationStates.isNotEmpty()) {
                        PropertyRow("OXIDATION STATES", element.oxidationStates.joinToString(", "))
                    }
                    element.standardElectrodePotential?.let { PropertyRow("ELECTRODE POTENTIAL", "$it V") }
                    element.crystalStructure?.let { PropertyRow("CRYSTAL STRUCTURE", it) }
                }

                // ===== THERMAL PROPERTIES =====
                if (element.thermalConductivity != null || element.heatOfFusion != null) {
                    SectionDivider()
                    SectionHeader("THERMAL PROPERTIES")
                    Spacer(modifier = Modifier.height(10.dp))

                    element.thermalConductivity?.let { PropertyRow("THERMAL CONDUCTIVITY", "$it W/(m·K)") }
                    element.heatOfFusion?.let { PropertyRow("HEAT OF FUSION", "$it kJ/mol") }
                    element.heatOfVaporization?.let { PropertyRow("HEAT OF VAPORIZATION", "$it kJ/mol") }
                    element.molarHeatCapacity?.let { PropertyRow("MOLAR HEAT CAPACITY", "$it J/(mol·K)") }
                }

                // ===== REAL WORLD USES =====
                if (element.realWorldUses.isNotEmpty()) {
                    SectionDivider()
                    SectionHeader("REAL WORLD USES")
                    Spacer(modifier = Modifier.height(10.dp))

                    element.realWorldUses.forEach { use ->
                        BulletItem(use)
                    }
                }

                // ===== WHERE FOUND =====
                if (element.whereFound.isNotEmpty()) {
                    SectionDivider()
                    SectionHeader("WHERE FOUND")
                    Spacer(modifier = Modifier.height(10.dp))

                    element.whereFound.forEach { location ->
                        BulletItem(location)
                    }
                }

                // ===== FUN FACTS =====
                if (element.trivia.isNotEmpty()) {
                    SectionDivider()
                    SectionHeader("FUN FACTS")
                    Spacer(modifier = Modifier.height(10.dp))

                    element.trivia.forEach { fact ->
                        TriviaItem(fact)
                    }
                }

                // ===== ABUNDANCE =====
                if (element.abundanceCrust != null || element.abundanceUniverse != null) {
                    SectionDivider()
                    SectionHeader("ABUNDANCE")
                    Spacer(modifier = Modifier.height(10.dp))

                    element.abundanceUniverse?.let { PropertyRow("UNIVERSE", formatAbundance(it)) }
                    element.abundanceCrust?.let { PropertyRow("EARTH'S CRUST", formatAbundance(it)) }
                    element.abundanceOcean?.let { PropertyRow("OCEAN", formatAbundance(it)) }
                    element.abundanceHuman?.let { PropertyRow("HUMAN BODY", formatAbundance(it)) }
                }

                // ===== ISOTOPES =====
                if (element.isotopes.isNotEmpty()) {
                    SectionDivider()
                    SectionHeader("ISOTOPES")
                    Spacer(modifier = Modifier.height(10.dp))

                    val stableIsotopes = element.isotopes.filter { it.stable }
                    val radioactiveIsotopes = element.isotopes.filter { !it.stable && it.halfLife != null }

                    PropertyRow("TOTAL ISOTOPES", element.isotopes.size.toString())
                    PropertyRow("STABLE", stableIsotopes.size.toString())
                    PropertyRow("RADIOACTIVE", radioactiveIsotopes.size.toString())

                    if (stableIsotopes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        PropertyRow("STABLE ISOTOPES", stableIsotopes.take(5).joinToString(", ") { "${it.massNumber}" } + if (stableIsotopes.size > 5) "..." else "")
                        stableIsotopes.filter { it.abundance > 0 }.take(3).forEach { isotope ->
                            PropertyRow("  ${element.symbol}-${isotope.massNumber} ABUNDANCE", "${String.format("%.2f", isotope.abundance)}%")
                        }
                    }

                    if (radioactiveIsotopes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        PropertyRow("RADIOACTIVE ISOTOPES", radioactiveIsotopes.take(5).joinToString(", ") { "${it.massNumber}" } + if (radioactiveIsotopes.size > 5) "..." else "")
                        radioactiveIsotopes.filter { it.halfLife != null }.take(2).forEach { isotope ->
                            val halfLifeText = "${isotope.halfLife}${isotope.halfLifeUnit?.let { " $it" } ?: ""}"
                            PropertyRow("  ${element.symbol}-${isotope.massNumber} HALF-LIFE", halfLifeText)
                        }
                    }
                }

                SectionDivider()

                // ===== LINKS =====
                Spacer(modifier = Modifier.height(8.dp))
                element.wikipediaUrl?.let {
                    Text(
                        text = "VIEW SOURCE DATA \u2192",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DetailColors.accent,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ============================================
// ELEMENT HEADER
// ============================================

@Composable
private fun ElementHeader(element: Element) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Left side: Name and category
        Column(modifier = Modifier.weight(1f)) {
            // Element name with symbol
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = element.name,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DetailColors.textPrimary
                )
                Text(
                    text = " (${element.symbol})",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = DetailColors.textSecondary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Category
            Text(
                text = element.category.displayName.uppercase(),
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = DetailColors.textSecondary,
                letterSpacing = 1.5.sp
            )
        }

        // Right side: Atomic number
        Text(
            text = "#${element.atomicNumber}",
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = DetailColors.textSecondary
        )
    }
}

// ============================================
// SECTION COMPONENTS
// ============================================

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = FontFamily.Monospace,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = DetailColors.textPrimary,
        letterSpacing = 1.5.sp
    )
}

@Composable
private fun SectionDivider() {
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider(
        color = DetailColors.divider,
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun PropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = DetailColors.textSecondary,
            letterSpacing = 0.5.sp,
            modifier = Modifier.weight(0.55f)
        )
        Text(
            text = value,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = DetailColors.textPrimary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.45f)
        )
    }
}

// ============================================
// LIST ITEM COMPONENTS
// ============================================

@Composable
private fun BulletItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        Text(
            text = "•",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = DetailColors.textSecondary,
            modifier = Modifier.width(16.dp)
        )
        Text(
            text = text,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = DetailColors.textPrimary,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun TriviaItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "★",
            fontFamily = FontFamily.Monospace,
            fontSize = 10.sp,
            color = DetailColors.accent,
            modifier = Modifier.width(16.dp)
        )
        Text(
            text = text,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontStyle = FontStyle.Italic,
            color = DetailColors.textPrimary,
            lineHeight = 16.sp
        )
    }
}

// ============================================
// UTILITY FUNCTIONS
// ============================================

/**
 * Format abundance value (ppb) to human-readable form
 */
private fun formatAbundance(ppb: Double): String {
    return when {
        ppb >= 10_000_000 -> String.format("%.1f%%", ppb / 10_000_000)
        ppb >= 1_000_000 -> String.format("%.0f ppm", ppb / 1000)
        ppb >= 1000 -> String.format("%.1f ppm", ppb / 1000)
        else -> String.format("%.0f ppb", ppb)
    }
}
