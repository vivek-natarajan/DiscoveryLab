package com.example.discoverylab.content.periodictable.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.periodictable.data.Element
import com.example.discoverylab.content.periodictable.data.ElementCategory

/**
 * Color palette matching Google Arts Periodic Table exactly.
 */
object ElementColors {
    // From the reference image legend (left to right)
    val alkaliMetal = Color(0xFFE53935)           // Red
    val alkalineEarthMetal = Color(0xFFFF7043)    // Orange-red
    val transitionMetal = Color(0xFF42A5F5)       // Light blue
    val postTransitionMetal = Color(0xFF26A69A)   // Teal
    val metalloid = Color(0xFFFFB74D)             // Orange/Yellow
    val reactiveNonmetal = Color(0xFF4DB6AC)      // Cyan/Turquoise
    val nobleGas = Color(0xFF66BB6A)              // Green
    val lanthanide = Color(0xFFEC407A)            // Pink
    val actinide = Color(0xFFEF5350)              // Red/Pink
    val unknown = Color(0xFFFFA726)               // Orange

    // Text colors
    val elementName = Color(0xFF757575)           // Gray for element names
    val atomicNumber = Color(0xFF9E9E9E)          // Lighter gray for atomic numbers
}

/**
 * Returns the category color matching Google Arts design.
 */
fun ElementCategory.toColor(): Color {
    return when (this) {
        ElementCategory.ALKALI_METAL -> ElementColors.alkaliMetal
        ElementCategory.ALKALINE_EARTH_METAL -> ElementColors.alkalineEarthMetal
        ElementCategory.TRANSITION_METAL -> ElementColors.transitionMetal
        ElementCategory.POST_TRANSITION_METAL -> ElementColors.postTransitionMetal
        ElementCategory.METALLOID -> ElementColors.metalloid
        ElementCategory.NONMETAL -> ElementColors.reactiveNonmetal
        ElementCategory.HALOGEN -> ElementColors.reactiveNonmetal  // Grouped with reactive nonmetals
        ElementCategory.NOBLE_GAS -> ElementColors.nobleGas
        ElementCategory.LANTHANIDE -> ElementColors.lanthanide
        ElementCategory.ACTINIDE -> ElementColors.actinide
        ElementCategory.UNKNOWN -> ElementColors.unknown
    }
}

// Compatibility aliases
fun ElementCategory.toPrimaryColor(): Color = toColor()
fun ElementCategory.toLightColor(): Color = toColor().copy(alpha = 0.1f)

@Composable
fun ElementCategory.toGradientColors(): Pair<Color, Color> = Pair(toColor().copy(alpha = 0.1f), toColor())

@Composable
fun ElementCategory.toBackgroundColor(): Color = toColor().copy(alpha = 0.1f)

@Composable
fun ElementCategory.toAccentColor(): Color = toColor()

/**
 * Element card with monospace typography matching reference design.
 * White background, colored text, minimal style.
 */
@Composable
fun ElementCard(
    element: Element,
    modifier: Modifier = Modifier,
    isDimmed: Boolean = false,
    onClick: () -> Unit = {}
) {
    val categoryColor = element.category.toColor()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(stiffness = 400f),
        label = "scale"
    )

    // Apply dimming effect when category filter is active
    val alpha = if (isDimmed) 0.25f else 1f

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 44.dp, minHeight = 56.dp)
            .aspectRatio(0.8f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .clip(RoundedCornerShape(2.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFBDBDBD),
                shape = RoundedCornerShape(2.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 2.dp, vertical = 3.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Atomic number - top left
            Text(
                text = element.atomicNumber.toString(),
                fontFamily = FontFamily.Default,
                fontSize = 7.sp,
                fontWeight = FontWeight.Normal,
                color = categoryColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 1.dp)
            )

            // Symbol - center, bold, category color
            Text(
                text = element.symbol,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = categoryColor,
                textAlign = TextAlign.Center
            )

            // Element name - must be visible
            Text(
                text = element.name,
                fontFamily = FontFamily.Default,
                fontSize = 7.sp,
                fontWeight = FontWeight.Normal,
                color = ElementColors.elementName,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Empty cell placeholder.
 */
@Composable
fun EmptyElementCell(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 44.dp, minHeight = 52.dp)
            .aspectRatio(0.85f)
    )
}

/**
 * Lanthanide/Actinide placeholder cell with monospace typography.
 */
@Composable
fun LanthanideActinidePlaceholder(
    isLanthanide: Boolean,
    modifier: Modifier = Modifier,
    isDimmed: Boolean = false,
    onClick: () -> Unit = {}
) {
    val color = if (isLanthanide) ElementColors.lanthanide else ElementColors.actinide
    val range = if (isLanthanide) "57-71" else "89-103"
    val alpha = if (isDimmed) 0.25f else 1f

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 44.dp, minHeight = 52.dp)
            .aspectRatio(0.85f)
            .graphicsLayer { this.alpha = alpha }
            .clip(RoundedCornerShape(2.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFBDBDBD),
                shape = RoundedCornerShape(2.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = range,
            fontFamily = FontFamily.Monospace,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}
