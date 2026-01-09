package com.example.discoverylab.content.periodictable

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.periodictable.data.Element
import com.example.discoverylab.content.periodictable.data.ElementCategory
import com.example.discoverylab.content.periodictable.data.ElementsRepository
import com.example.discoverylab.content.periodictable.ui.ElementDetailPanel
import com.example.discoverylab.content.periodictable.ui.PeriodicTableGrid
import com.example.discoverylab.content.periodictable.ui.components.ElementColors

// ============================================
// COLOR PALETTE (Muted Sage Green Theme)
// ============================================

object TableColors {
    val background = Color(0xFFD4E4D4)      // Muted sage green
    val surface = Color(0xFFD4E4D4)
    val textPrimary = Color(0xFF2A2A2A)     // Dark gray
    val textSecondary = Color(0xFF6A6A6A)   // Medium gray
    val divider = Color(0xFFB0C0B0)         // Light gray-green
}

@Composable
fun PeriodicTableScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val elements = remember { ElementsRepository.loadElements(context) }
    var selectedElementIndex by remember { mutableIntStateOf(0) }  // Default to first element
    var selectedCategory by remember { mutableStateOf<ElementCategory?>(null) }

    val selectedElement = if (selectedElementIndex >= 0 && selectedElementIndex < elements.size) {
        elements[selectedElementIndex]
    } else elements.firstOrNull()

    // Lock to landscape mode
    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(TableColors.background)
    ) {
        // Left side: Periodic table
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            // Periodic table grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                PeriodicTableGrid(
                    elements = elements,
                    highlightedCategory = selectedCategory,
                    onElementClick = { element ->
                        selectedElementIndex = elements.indexOf(element)
                    }
                )
            }

            // Legend bar at bottom
            LegendBar(
                selectedCategory = selectedCategory,
                onCategoryClick = { category ->
                    selectedCategory = if (selectedCategory == category) null else category
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }

        // Right side: Element detail panel (always visible)
        selectedElement?.let { element ->
            ElementDetailPanel(
                element = element,
                onClose = { /* No-op, always visible */ },
                onPrevious = {
                    if (selectedElementIndex > 0) {
                        selectedElementIndex--
                    }
                },
                onNext = {
                    if (selectedElementIndex < elements.size - 1) {
                        selectedElementIndex++
                    }
                },
                hasPrevious = selectedElementIndex > 0,
                hasNext = selectedElementIndex < elements.size - 1
            )
        }
    }
}

/**
 * Bottom legend bar with monospace typography
 */
@Composable
private fun LegendBar(
    selectedCategory: ElementCategory?,
    onCategoryClick: (ElementCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LegendItem("Alkali Metal", ElementColors.alkaliMetal, ElementCategory.ALKALI_METAL, selectedCategory, onCategoryClick)
        LegendItem("Alkaline Earth Metal", ElementColors.alkalineEarthMetal, ElementCategory.ALKALINE_EARTH_METAL, selectedCategory, onCategoryClick)
        LegendItem("Transition Metal", ElementColors.transitionMetal, ElementCategory.TRANSITION_METAL, selectedCategory, onCategoryClick)
        LegendItem("Post-transition Metal", ElementColors.postTransitionMetal, ElementCategory.POST_TRANSITION_METAL, selectedCategory, onCategoryClick)
        LegendItem("Metalloid", ElementColors.metalloid, ElementCategory.METALLOID, selectedCategory, onCategoryClick)
        LegendItem("Reactive Nonmetal", ElementColors.reactiveNonmetal, ElementCategory.NONMETAL, selectedCategory, onCategoryClick)
        LegendItem("Noble Gas", ElementColors.nobleGas, ElementCategory.NOBLE_GAS, selectedCategory, onCategoryClick)
        LegendItem("Lanthanide", ElementColors.lanthanide, ElementCategory.LANTHANIDE, selectedCategory, onCategoryClick)
        LegendItem("Actinide", ElementColors.actinide, ElementCategory.ACTINIDE, selectedCategory, onCategoryClick)
    }
}

@Composable
private fun LegendItem(
    name: String,
    color: Color,
    category: ElementCategory,
    selectedCategory: ElementCategory?,
    onClick: (ElementCategory) -> Unit
) {
    val isSelected = selectedCategory == category

    Row(
        modifier = Modifier
            .clickable { onClick(category) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = name,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = if (isSelected) color else TableColors.textSecondary,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
