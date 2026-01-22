package com.example.discoverylab.content.solarsystem.ui.home

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.solarsystem.data.CelestialBody
import com.example.discoverylab.content.solarsystem.ui.components.PropertyRow
import com.example.discoverylab.content.solarsystem.ui.components.SectionHeader
import com.example.discoverylab.content.solarsystem.ui.components.SolarSystemImage
import com.example.discoverylab.content.solarsystem.ui.components.TriviaItem
import com.example.discoverylab.content.solarsystem.ui.components.formatNumber
import com.example.discoverylab.content.solarsystem.ui.theme.SolarColors

/**
 * Home view showing solar system image with Sun info below.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(
    context: Context,
    bodies: List<CelestialBody>,
    sunBody: CelestialBody?,
    onBodySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SolarColors.background)
    ) {
        // Solar System Image with clickable hotspots
        SolarSystemImage(
            context = context,
            selectedBodyId = null,
            onBodySelected = onBodySelected,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f)
        )

        // Sun info section below
        sunBody?.let { body ->
            HorizontalDivider(color = SolarColors.divider, thickness = 1.dp)

            // Title
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "THE SUN",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp,
                    color = SolarColors.textPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = body.mediumDescription,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = SolarColors.textSecondary,
                    lineHeight = 16.sp
                )
            }

            HorizontalDivider(color = SolarColors.divider, thickness = 1.dp)

            // Two-column content for Sun
            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                Row(
                    modifier = Modifier
                        .weight(0.55f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // LEFT COLUMN
                    Column(modifier = Modifier.weight(0.38f)) {
                        SectionHeader("PHYSICAL PROPERTIES")
                        Spacer(modifier = Modifier.height(8.dp))

                        if (body.diameter > 0) {
                            PropertyRow("Diameter", "${String.format("%,.0f", body.diameter)} km")
                        }
                        body.physicalCharacteristics?.mass?.let { mass ->
                            mass.earthComparison?.let {
                                PropertyRow("Mass", "${formatNumber(it)}x Earth")
                            }
                        }
                        body.temperature?.photosphere?.let {
                            PropertyRow("Surface Temp", "${String.format("%,d", it.value)}°C")
                        }
                        body.temperature?.core?.let {
                            PropertyRow("Core Temp", "${String.format("%,d", it.value)}°C")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        SectionHeader("COMPOSITION")
                        Spacer(modifier = Modifier.height(8.dp))
                        body.composition?.let { comp ->
                            comp.hydrogen?.let { PropertyRow("Hydrogen", "${formatNumber(it)}%") }
                            comp.helium?.let { PropertyRow("Helium", "${formatNumber(it)}%") }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        SectionHeader("LIFESPAN")
                        Spacer(modifier = Modifier.height(8.dp))
                        body.lifespan?.let { life ->
                            life.currentAge?.let { PropertyRow("Current Age", it) }
                            life.remainingLife?.let { PropertyRow("Remaining", it) }
                        }
                    }

                    // RIGHT COLUMN
                    Column(modifier = Modifier.weight(0.62f)) {
                        if (body.funFacts.isNotEmpty()) {
                            SectionHeader("FUN FACTS")
                            Spacer(modifier = Modifier.height(8.dp))
                            body.funFacts.take(5).forEach { fact ->
                                TriviaItem(fact)
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }

                        body.mythology?.roman?.let { myth ->
                            Spacer(modifier = Modifier.height(16.dp))
                            SectionHeader("MYTHOLOGY")
                            Spacer(modifier = Modifier.height(8.dp))
                            myth.namedAfter?.let { PropertyRow("Named After", it) }
                            myth.story?.let { story ->
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = story,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = SolarColors.textSecondary,
                                    lineHeight = 14.sp
                                )
                            }
                        }

                        body.mythology?.indian?.let { myth ->
                            Spacer(modifier = Modifier.height(16.dp))
                            SectionHeader("HINDU MYTHOLOGY")
                            Spacer(modifier = Modifier.height(8.dp))
                            myth.names?.firstOrNull()?.let { PropertyRow("Sanskrit Name", it) }
                            myth.meaning?.let { PropertyRow("Meaning", it) }
                            myth.role?.let { role ->
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = role,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = SolarColors.textSecondary,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
