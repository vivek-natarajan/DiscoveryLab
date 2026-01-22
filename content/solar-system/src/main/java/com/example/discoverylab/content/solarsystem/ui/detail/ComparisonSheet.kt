package com.example.discoverylab.content.solarsystem.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.solarsystem.data.CelestialBody
import com.example.discoverylab.content.solarsystem.ui.components.WheelPicker
import com.example.discoverylab.content.solarsystem.ui.components.formatNumber
import com.example.discoverylab.content.solarsystem.ui.theme.SolarColors

/**
 * Planet comparison component showing properties of two planets side by side.
 */
@Composable
fun ComparisonContent(
    currentBody: CelestialBody,
    allBodies: List<CelestialBody>,
    comparisonBodyId: String?,
    onComparisonBodyChange: (String?) -> Unit,
    onClose: () -> Unit
) {
    // Filter to only planets (exclude sun and moon)
    val selectablePlanets = allBodies
        .filter { it.id != "sun" && it.id != "moon" }
        .sortedBy { it.orderFromSun }

    // Find selected comparison planet
    val selectedIndex = comparisonBodyId?.let { id ->
        selectablePlanets.indexOfFirst { it.id == id }.takeIf { it >= 0 }
    } ?: 0
    val comparisonBody = selectablePlanets.getOrNull(selectedIndex)

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(280.dp)
        ) {
            // Header row with current planet, wheel picker for comparison, and close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Current planet name (fixed)
                Text(
                    text = currentBody.name.uppercase(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SolarColors.textPrimary
                )

                Text(
                    text = "vs",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = SolarColors.textSecondary
                )

                // Inline wheel picker for comparison planet
                WheelPicker(
                    items = selectablePlanets,
                    selectedIndex = selectedIndex,
                    onSelectionChange = { index ->
                        onComparisonBodyChange(selectablePlanets[index].id)
                    },
                    itemToString = { it.name.uppercase() },
                    width = 100.dp,
                    height = 72.dp
                )

                // Close button
                Text(
                    text = "×",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    color = SolarColors.textSecondary,
                    modifier = Modifier
                        .clickable { onClose() }
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = SolarColors.divider, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Comparison data
            if (comparisonBody != null) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Diameter
                    ComparisonRowBothValues(
                        label = "Diameter",
                        value1 = currentBody.diameter,
                        value2 = comparisonBody.diameter,
                        unit = "km",
                        formatAsRatio = true
                    )
                    // Gravity
                    ComparisonRowBothValues(
                        label = "Gravity",
                        value1 = currentBody.surfaceGravity,
                        value2 = comparisonBody.surfaceGravity,
                        unit = "m/s²",
                        formatAsRatio = true
                    )
                    // Density
                    val density1 = currentBody.physicalCharacteristics?.density?.value
                    val density2 = comparisonBody.physicalCharacteristics?.density?.value
                    if (density1 != null && density2 != null) {
                        ComparisonRowBothValues(
                            label = "Density",
                            value1 = density1,
                            value2 = density2,
                            unit = "kg/m³",
                            formatAsRatio = true
                        )
                    }
                    // Temperature
                    val temp1 = currentBody.physicalCharacteristics?.temperature?.mean
                    val temp2 = comparisonBody.physicalCharacteristics?.temperature?.mean
                    if (temp1 != null && temp2 != null) {
                        ComparisonRowBothValues(
                            label = "Temp",
                            value1 = temp1,
                            value2 = temp2,
                            unit = "°C",
                            formatAsRatio = false
                        )
                    }
                    // Orbital Distance
                    val dist1 = currentBody.orbitalCharacteristics?.distanceFromSun?.semimajorAxis
                    val dist2 = comparisonBody.orbitalCharacteristics?.distanceFromSun?.semimajorAxis
                    if (dist1 != null && dist2 != null) {
                        ComparisonRowBothValues(
                            label = "Orbit Dist",
                            value1 = dist1,
                            value2 = dist2,
                            unit = "M km",
                            formatAsRatio = true
                        )
                    }
                    // Year Length (orbital period)
                    val year1 = currentBody.orbitalCharacteristics?.orbitalPeriod?.value
                    val year2 = comparisonBody.orbitalCharacteristics?.orbitalPeriod?.value
                    if (year1 != null && year2 != null) {
                        ComparisonRowBothValues(
                            label = "Year",
                            value1 = year1,
                            value2 = year2,
                            unit = "days",
                            formatAsRatio = true
                        )
                    }
                    // Day Length (rotation period)
                    val day1 = currentBody.orbitalCharacteristics?.rotationPeriod?.value
                    val day2 = comparisonBody.orbitalCharacteristics?.rotationPeriod?.value
                    if (day1 != null && day2 != null) {
                        ComparisonRowBothValues(
                            label = "Day",
                            value1 = day1,
                            value2 = day2,
                            unit = "hrs",
                            formatAsRatio = true
                        )
                    }
                    // Axial Tilt
                    val tilt1 = currentBody.orbitalCharacteristics?.axialTilt?.value
                    val tilt2 = comparisonBody.orbitalCharacteristics?.axialTilt?.value
                    if (tilt1 != null && tilt2 != null) {
                        ComparisonRowBothValues(
                            label = "Axial Tilt",
                            value1 = tilt1,
                            value2 = tilt2,
                            unit = "°",
                            formatAsRatio = false
                        )
                    }
                    // Moons
                    ComparisonRowBothValues(
                        label = "Moons",
                        value1 = currentBody.moonCount.toDouble(),
                        value2 = comparisonBody.moonCount.toDouble(),
                        unit = "",
                        formatAsRatio = false
                    )
                }
            }
        }
    }
}

@Composable
private fun ComparisonRowBothValues(
    label: String,
    value1: Double,
    value2: Double,
    unit: String,
    formatAsRatio: Boolean
) {
    val variance = if (formatAsRatio && value1 != 0.0) {
        val ratio = value2 / value1
        when {
            ratio >= 100 -> "${ratio.toInt()}x"
            ratio >= 10 -> "${String.format("%.0f", ratio)}x"
            ratio >= 1 -> "${String.format("%.1f", ratio)}x"
            ratio >= 0.1 -> "${String.format("%.2f", ratio)}x"
            else -> "${String.format("%.3f", ratio)}x"
        }
    } else {
        val diff = value2 - value1
        when {
            diff >= 0 -> "+${formatNumber(diff)}"
            else -> formatNumber(diff)
        }
    }

    // Format values with units
    val formattedValue1 = if (unit.isNotEmpty()) "${formatNumber(value1)} $unit" else formatNumber(value1)
    val formattedValue2 = if (unit.isNotEmpty()) "${formatNumber(value2)} $unit" else formatNumber(value2)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Value 1 (current planet) with unit
        Text(
            text = formattedValue1,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = SolarColors.textPrimary,
            modifier = Modifier.weight(0.3f)
        )
        // Label in center
        Text(
            text = label,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = SolarColors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.2f)
        )
        // Value 2 (comparison planet) with unit
        Text(
            text = formattedValue2,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = SolarColors.textPrimary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.3f)
        )
        // Variance
        Text(
            text = variance,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = SolarColors.textSecondary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.2f)
        )
    }
}
