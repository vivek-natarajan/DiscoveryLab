package com.example.discoverylab.content.solarsystem.ui.detail

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.solarsystem.data.CelestialBody
import com.example.discoverylab.content.solarsystem.ui.components.BulletItem
import com.example.discoverylab.content.solarsystem.ui.components.PropertyRow
import com.example.discoverylab.content.solarsystem.ui.components.SectionHeader
import com.example.discoverylab.content.solarsystem.ui.components.TriviaItem
import com.example.discoverylab.content.solarsystem.ui.components.formatNumber
import com.example.discoverylab.content.solarsystem.ui.theme.SolarColors

// Bottom tool selection
private enum class BottomTool {
    WEIGHT_CALCULATOR,
    COMPARISON
}

/**
 * Full-screen detail view for planets.
 * Hero image with overlaid name/description, two-column content below.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullScreenDetailView(
    body: CelestialBody,
    allBodies: List<CelestialBody>,
    context: Context,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    // Filter out moon and sun from navigation - only planets
    val sortedBodies = allBodies.filter { it.id != "moon" && it.id != "sun" }.sortedBy { it.orderFromSun }
    val currentIndex = sortedBodies.indexOfFirst { it.id == body.id }
    val prevBody = if (currentIndex > 0) sortedBodies[currentIndex - 1] else null
    val nextBody = if (currentIndex < sortedBodies.size - 1) sortedBodies[currentIndex + 1] else null

    // Swipe gesture state
    var dragOffset by remember { mutableFloatStateOf(0f) }
    val swipeThreshold = 100f

    // Bottom bar state
    var activeBottomTool by remember { mutableStateOf<BottomTool?>(null) }
    var earthWeight by remember { mutableFloatStateOf(70f) }
    var comparisonBodyId by remember { mutableStateOf<String?>(null) }

    // Get Earth's gravity from data (fallback to 9.8 if not found)
    val earthBody = allBodies.find { it.id == "earth" }
    val earthGravity = earthBody?.surfaceGravity ?: 9.8

    // Load hero image
    val heroImage = remember(body.id) {
        try {
            context.assets.open("${body.id}.png").use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
            }
        } catch (e: Exception) {
            null
        }
    }

    // Get display order number
    val orderNumber = body.orderFromSun

    // Calculate weight on this planet using Earth's actual gravity from data
    val weightOnPlanet = if (body.surfaceGravity > 0 && earthGravity > 0) {
        earthWeight * (body.surfaceGravity / earthGravity)
    } else null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SolarColors.background)
            .pointerInput(body.id) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (dragOffset > swipeThreshold && prevBody != null) {
                            onNavigate(prevBody.id)
                        } else if (dragOffset < -swipeThreshold && nextBody != null) {
                            onNavigate(nextBody.id)
                        }
                        dragOffset = 0f
                    },
                    onDragCancel = { dragOffset = 0f },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset += dragAmount
                    }
                )
            }
    ) {
        // ===== HERO IMAGE with text overlaid =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f)
        ) {
            // Hero image - fills entire area
            heroImage?.let {
                Image(
                    bitmap = it,
                    contentDescription = body.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Planet name at top center
            Text(
                text = "${orderNumber}. ${body.name.uppercase()}",
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp)
            )

            // Description - narrower width, positioned above bottom
            Text(
                text = body.mediumDescription,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 20.dp)
            )

            // Back button
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp, start = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Navigation arrows
            if (prevBody != null) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous",
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .size(20.dp)
                )
            }
            if (nextBody != null) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .size(20.dp)
                )
            }
        }

        HorizontalDivider(color = SolarColors.divider, thickness = 1.dp)

        // ===== TWO-COLUMN CONTENT (no overscroll effect) =====
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // LEFT COLUMN: Physical, Orbital, Atmosphere, Moons
                Column(modifier = Modifier.weight(0.38f)) {
                    // Physical Properties
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
                    if (body.surfaceGravity > 0) {
                        PropertyRow("Gravity", "${formatNumber(body.surfaceGravity)} m/s²")
                    }
                    body.physicalCharacteristics?.density?.let {
                        PropertyRow("Density", "${it.value.toInt()} kg/m³")
                    }
                    body.physicalCharacteristics?.temperature?.mean?.let {
                        PropertyRow("Temperature", "${formatNumber(it)}°C")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Orbital Properties
                    SectionHeader("ORBITAL PROPERTIES")
                    Spacer(modifier = Modifier.height(8.dp))

                    body.orbitalCharacteristics?.distanceFromSun?.semimajorAxis?.let {
                        PropertyRow("Distance", "${formatNumber(it)} million km")
                    }
                    body.orbitalCharacteristics?.orbitalPeriod?.value?.let {
                        val days = it.toInt()
                        val years = days / 365.25
                        val periodStr = if (years >= 1) {
                            "${formatNumber(years)} years"
                        } else {
                            "$days days"
                        }
                        PropertyRow("Year Length", periodStr)
                    }
                    body.orbitalCharacteristics?.orbitalVelocity?.let {
                        PropertyRow("Velocity", "${formatNumber(it.mean)} km/s")
                    }
                    body.orbitalCharacteristics?.rotationPeriod?.let {
                        PropertyRow("Day Length", "${formatNumber(it.value)} hours")
                    }
                    body.orbitalCharacteristics?.axialTilt?.let {
                        PropertyRow("Axial Tilt", "${formatNumber(it.value)}°")
                    }

                    // Atmosphere
                    body.atmosphere?.let { atm ->
                        if (atm.composition.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            SectionHeader("ATMOSPHERE")
                            Spacer(modifier = Modifier.height(8.dp))

                            atm.composition.take(4).forEach { component ->
                                PropertyRow(component.gas, "${formatNumber(component.percentage)}%")
                            }
                        }
                    }

                    // Moons
                    if (body.moonCount > 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader("MOONS")
                        Spacer(modifier = Modifier.height(8.dp))

                        PropertyRow("Total", body.moonCount.toString())
                        body.moons?.majorMoons?.take(4)?.forEach { moon ->
                            BulletItem("${moon.name}${moon.radius?.let { " (${formatNumber(it)} km)" } ?: ""}")
                        }
                    }
                }

                // RIGHT COLUMN: Fun Facts + Mythology
                Column(modifier = Modifier.weight(0.62f)) {
                    // Fun Facts
                    if (body.funFacts.isNotEmpty()) {
                        SectionHeader("FUN FACTS")
                        Spacer(modifier = Modifier.height(8.dp))

                        body.funFacts.take(5).forEach { fact ->
                            TriviaItem(fact)
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }

                    // Roman Mythology
                    body.mythology?.roman?.let { myth ->
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader("ROMAN MYTHOLOGY")
                        Spacer(modifier = Modifier.height(8.dp))

                        myth.namedAfter?.let {
                            PropertyRow("Named After", it)
                        }
                        myth.story?.let { story ->
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = story,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                color = SolarColors.textSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // Hindu Mythology
                    body.mythology?.indian?.let { myth ->
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader("HINDU MYTHOLOGY")
                        Spacer(modifier = Modifier.height(8.dp))

                        myth.names?.firstOrNull()?.let {
                            PropertyRow("Sanskrit Name", it)
                        }
                        myth.role?.let { role ->
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = role,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                color = SolarColors.textSecondary,
                                lineHeight = 16.sp
                            )
                        }
                        myth.birthStory?.let { story ->
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = story,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                color = SolarColors.textSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // ===== BOTTOM BAR: Tools =====
        HorizontalDivider(color = SolarColors.divider, thickness = 1.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SolarColors.background)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            when (activeBottomTool) {
                null -> {
                    // Show both tool buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "WEIGHT",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            color = SolarColors.textSecondary,
                            modifier = Modifier
                                .clickable { activeBottomTool = BottomTool.WEIGHT_CALCULATOR }
                                .border(1.dp, SolarColors.divider, RoundedCornerShape(4.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "COMPARE",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            color = SolarColors.textSecondary,
                            modifier = Modifier
                                .clickable { activeBottomTool = BottomTool.COMPARISON }
                                .border(1.dp, SolarColors.divider, RoundedCornerShape(4.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                BottomTool.WEIGHT_CALCULATOR -> {
                    WeightCalculatorContent(
                        body = body,
                        earthWeight = earthWeight,
                        weightOnPlanet = weightOnPlanet,
                        onWeightChange = { earthWeight = it },
                        onClose = { activeBottomTool = null }
                    )
                }

                BottomTool.COMPARISON -> {
                    ComparisonContent(
                        currentBody = body,
                        allBodies = allBodies,
                        comparisonBodyId = comparisonBodyId,
                        onComparisonBodyChange = { comparisonBodyId = it },
                        onClose = { activeBottomTool = null }
                    )
                }
            }
        }
    }
}
