package com.example.discoverylab.content.periodictable.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.periodictable.data.Element
import com.example.discoverylab.content.periodictable.data.StateOfMatter
import com.example.discoverylab.content.periodictable.ui.components.toColor
import kotlin.math.cos
import kotlin.math.sin

/**
 * Full-screen element detail view with organized sections.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ElementDetailScreen(
    element: Element,
    onBack: () -> Unit
) {
    val primaryColor = element.category.toColor()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Back button
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF424242)
            )
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            // ===== HEADER SECTION =====
            // Title: "Hydrogen (H)¹"
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${element.name} (${element.symbol})",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Text(
                    text = element.atomicNumber.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Category with colored circle
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(primaryColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = element.category.displayName,
                    fontSize = 16.sp,
                    color = Color(0xFF616161)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ===== QUICK FACTS ROW =====
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // State badge
                QuickFactChip(
                    label = element.naturalState.displayName,
                    icon = when (element.naturalState) {
                        StateOfMatter.SOLID -> "\u25A0"  // Square
                        StateOfMatter.LIQUID -> "\u25CF" // Circle
                        StateOfMatter.GAS -> "\u25CB"    // Empty circle
                        StateOfMatter.UNKNOWN -> "?"
                    },
                    color = primaryColor
                )

                // Appearance
                element.appearance?.let { appearance ->
                    if (appearance.isNotBlank()) {
                        QuickFactChip(
                            label = appearance.take(20) + if (appearance.length > 20) "..." else "",
                            color = Color(0xFF78909C)
                        )
                    }
                }

                // Discovery year
                element.discoveryYear?.let { year ->
                    QuickFactChip(
                        label = year.toString(),
                        color = Color(0xFF8D6E63)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ===== BOHR MODEL SECTION =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                BohrModelVisualization(
                    electronShells = element.electronShells,
                    primaryColor = primaryColor
                )
            }

            Text(
                text = "Bohr model representation",
                fontSize = 13.sp,
                color = Color(0xFF9E9E9E)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== DID YOU KNOW? SECTION =====
            if (element.trivia.isNotEmpty()) {
                SectionHeader(title = "Did You Know?", icon = "\u2728")

                element.trivia.forEachIndexed { index, fact ->
                    TriviaCard(fact = fact, index = index)
                    if (index < element.trivia.size - 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // ===== PROPERTIES SECTION =====
            SectionHeader(title = "Properties", icon = "\u2699")

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF5F5F5),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    PropertyRow(label = "Symbol", value = element.symbol)
                    PropertyDivider()
                    PropertyRow(label = "Atomic Mass", value = String.format("%.5f u", element.atomicMass))

                    element.density?.let { density ->
                        PropertyDivider()
                        PropertyRow(label = "Density", value = String.format("%.4f g/cm\u00B3", density))
                    }

                    element.meltingPoint?.let { mp ->
                        PropertyDivider()
                        PropertyRow(label = "Melting Point", value = String.format("%.2f \u00B0C", mp - 273.15))
                    }

                    element.boilingPoint?.let { bp ->
                        PropertyDivider()
                        PropertyRow(label = "Boiling Point", value = String.format("%.2f \u00B0C", bp - 273.15))
                    }

                    element.electronegativity?.let { en ->
                        PropertyDivider()
                        PropertyRow(label = "Electronegativity", value = String.format("%.2f", en))
                    }

                    PropertyDivider()
                    PropertyRow(label = "Electron Config", value = element.electronConfiguration)
                    PropertyDivider()
                    PropertyRow(label = "Electron Shells", value = element.electronShells.joinToString(", "))
                    PropertyDivider()
                    PropertyRow(label = "Period", value = element.period.toString())

                    element.group?.let { group ->
                        PropertyDivider()
                        PropertyRow(label = "Group", value = group.toString())
                    }

                    PropertyDivider()
                    PropertyRow(label = "Block", value = element.block.uppercase())
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ===== REAL WORLD SECTION =====
            if (element.realWorldUses.isNotEmpty() || element.whereFound.isNotEmpty()) {
                SectionHeader(title = "In The Real World", icon = "\uD83C\uDF0D")

                // Common Uses
                if (element.realWorldUses.isNotEmpty()) {
                    Text(
                        text = "Common Uses",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF616161),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    element.realWorldUses.forEach { use ->
                        BulletItem(text = use)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Found In
                if (element.whereFound.isNotEmpty()) {
                    Text(
                        text = "Found In",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF616161),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    element.whereFound.forEach { location ->
                        BulletItem(text = location)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // ===== DISCOVERY SECTION =====
            if (element.discoveredBy != null || element.discoveryYear != null) {
                SectionHeader(title = "Discovery", icon = "\uD83D\uDD0D")

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF8E1),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "\uD83D\uDD0D",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            element.discoveredBy?.let { discoverer ->
                                Text(
                                    text = "Discovered by $discoverer",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF424242)
                                )
                            }
                            element.discoveryYear?.let { year ->
                                Text(
                                    text = "in $year",
                                    fontSize = 14.sp,
                                    color = Color(0xFF757575)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    icon: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Text(
            text = icon,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF424242)
        )
    }
}

@Composable
private fun QuickFactChip(
    label: String,
    color: Color,
    icon: String? = null
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Text(
                    text = icon,
                    fontSize = 12.sp,
                    color = color
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

@Composable
private fun TriviaCard(
    fact: String,
    index: Int
) {
    val colors = listOf(
        Color(0xFFE3F2FD),  // Blue light
        Color(0xFFE8F5E9),  // Green light
        Color(0xFFFFF3E0),  // Orange light
        Color(0xFFFCE4EC)   // Pink light
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = colors[index % colors.size],
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "\u2022",
                fontSize = 16.sp,
                color = Color(0xFF424242)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = fact,
                fontSize = 14.sp,
                color = Color(0xFF424242),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun PropertyRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF424242)
        )
    }
}

@Composable
private fun PropertyDivider() {
    HorizontalDivider(
        color = Color(0xFFE0E0E0),
        thickness = 1.dp
    )
}

@Composable
private fun BulletItem(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "\u2022",
            fontSize = 14.sp,
            color = Color(0xFF616161),
            modifier = Modifier.padding(end = 8.dp, top = 2.dp)
        )
        Text(
            text = text,
            fontSize = 15.sp,
            color = Color(0xFF424242),
            lineHeight = 22.sp
        )
    }
}

/**
 * Simple Bohr model visualization showing electron shells.
 */
@Composable
private fun BohrModelVisualization(
    electronShells: List<Int>,
    primaryColor: Color
) {
    val shellColors = listOf(
        Color(0xFFE0E0E0),
        Color(0xFFBDBDBD),
        Color(0xFF9E9E9E),
        Color(0xFF757575),
        Color(0xFF616161),
        Color(0xFF424242),
        Color(0xFF212121)
    )

    Canvas(
        modifier = Modifier.size(160.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = size.minDimension / 2 - 16f

        // Draw shells (orbits)
        val shellCount = electronShells.size
        electronShells.forEachIndexed { index, electronCount ->
            val radius = maxRadius * (index + 1) / shellCount.coerceAtLeast(1)

            // Draw orbit circle
            drawCircle(
                color = shellColors.getOrElse(index) { Color.Gray },
                radius = radius,
                center = Offset(centerX, centerY),
                style = Stroke(width = 1.5f)
            )

            // Draw electrons on this shell
            for (i in 0 until electronCount) {
                val angle = (2 * Math.PI * i / electronCount) - Math.PI / 2
                val electronX = centerX + radius * cos(angle).toFloat()
                val electronY = centerY + radius * sin(angle).toFloat()

                // Electron dot
                drawCircle(
                    color = Color(0xFF4CAF50),
                    radius = 4f,
                    center = Offset(electronX, electronY)
                )
            }
        }

        // Draw nucleus
        drawCircle(
            color = primaryColor,
            radius = 14f,
            center = Offset(centerX, centerY)
        )
    }
}
