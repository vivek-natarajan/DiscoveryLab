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
import androidx.compose.foundation.layout.width
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
 * Weight calculator component showing weight on Earth vs selected planet.
 */
@Composable
fun WeightCalculatorContent(
    body: CelestialBody,
    earthWeight: Float,
    weightOnPlanet: Double?,
    onWeightChange: (Float) -> Unit,
    onClose: () -> Unit
) {
    // Weight options (10 to 200 in steps of 5)
    val weightOptions = (10..200 step 5).toList()
    val selectedIndex = weightOptions.indexOfFirst { it >= earthWeight.toInt() }.coerceAtLeast(0)

    // Calculate ratio
    val ratio = if (weightOnPlanet != null && earthWeight > 0) {
        weightOnPlanet / earthWeight
    } else null

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(180.dp)
        ) {
            // Header row with planet names and close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EARTH",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SolarColors.textPrimary,
                    modifier = Modifier.weight(0.3f)
                )

                Text(
                    text = "vs",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = SolarColors.textSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.2f)
                )

                Text(
                    text = body.name.uppercase(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SolarColors.textPrimary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(0.3f)
                )

                Text(
                    text = "×",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    color = SolarColors.textSecondary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(0.2f)
                        .clickable { onClose() }
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = SolarColors.divider, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Weight row with picker on left, result on right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Earth weight picker
                Box(modifier = Modifier.weight(0.3f)) {
                    WheelPicker(
                        items = weightOptions,
                        selectedIndex = selectedIndex,
                        onSelectionChange = { index ->
                            onWeightChange(weightOptions[index].toFloat())
                        },
                        itemToString = { "$it kg" },
                        width = 80.dp,
                        height = 72.dp
                    )
                }

                // Label
                Text(
                    text = "Weight",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = SolarColors.textSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.2f)
                )

                // Planet weight
                Text(
                    text = weightOnPlanet?.let { "${formatNumber(it)} kg" } ?: "—",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = SolarColors.textPrimary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(0.3f)
                )

                // Ratio
                Text(
                    text = ratio?.let {
                        if (it >= 1) "${String.format("%.1f", it)}x" else "${String.format("%.2f", it)}x"
                    } ?: "—",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = SolarColors.textSecondary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(0.2f)
                )
            }
        }
    }
}
