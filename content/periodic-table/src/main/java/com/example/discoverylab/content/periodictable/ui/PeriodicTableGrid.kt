package com.example.discoverylab.content.periodictable.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.discoverylab.content.periodictable.data.Element
import com.example.discoverylab.content.periodictable.data.ElementCategory
import com.example.discoverylab.content.periodictable.data.GridPosition
import com.example.discoverylab.content.periodictable.data.gridPosition
import com.example.discoverylab.content.periodictable.ui.components.ElementCard
import com.example.discoverylab.content.periodictable.ui.components.EmptyElementCell
import com.example.discoverylab.content.periodictable.ui.components.LanthanideActinidePlaceholder

// Grid colors matching the sage green theme
private object GridColors {
    val background = Color(0xFFD4E4D4)      // Muted sage green
}

/**
 * Periodic table grid - landscape only, fits entire table on screen.
 * 18 columns x 9 rows (7 main + 2 for lanthanides/actinides)
 */
@Composable
fun PeriodicTableGrid(
    elements: List<Element>,
    modifier: Modifier = Modifier,
    highlightedCategory: ElementCategory? = null,
    onElementClick: (Element) -> Unit = {}
) {
    // Build element lookup map once (filter out elements with invalid positions like 119+)
    val elementsByPosition = remember(elements) {
        elements
            .filter { it.gridPosition().row >= 0 && it.gridPosition().column >= 0 }
            .associateBy { it.gridPosition() }
    }

    val spacing = 2.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GridColors.background)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        // Main table rows (periods 1-7)
        for (row in 0..6) {
            TableRow(
                row = row,
                elementsByPosition = elementsByPosition,
                highlightedCategory = highlightedCategory,
                onElementClick = onElementClick,
                spacing = spacing,
                modifier = Modifier.weight(1f)
            )
        }

        // Small gap before lanthanides/actinides
        Spacer(modifier = Modifier.height(4.dp))

        // Lanthanides row
        TableRow(
            row = 8,
            elementsByPosition = elementsByPosition,
            highlightedCategory = highlightedCategory,
            onElementClick = onElementClick,
            spacing = spacing,
            modifier = Modifier.weight(1f)
        )

        // Actinides row
        TableRow(
            row = 9,
            elementsByPosition = elementsByPosition,
            highlightedCategory = highlightedCategory,
            onElementClick = onElementClick,
            spacing = spacing,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TableRow(
    row: Int,
    elementsByPosition: Map<GridPosition, Element>,
    highlightedCategory: ElementCategory?,
    onElementClick: (Element) -> Unit,
    spacing: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        for (col in 0..17) {
            val position = GridPosition(row, col)
            val element = elementsByPosition[position]
            val isDimmed = highlightedCategory != null && element?.category != highlightedCategory

            Box(modifier = Modifier.weight(1f)) {
                when {
                    element != null -> {
                        ElementCard(
                            element = element,
                            isDimmed = isDimmed,
                            onClick = { onElementClick(element) }
                        )
                    }
                    row == 5 && col == 2 -> {
                        LanthanideActinidePlaceholder(
                            isLanthanide = true,
                            isDimmed = highlightedCategory != null && highlightedCategory != ElementCategory.LANTHANIDE
                        )
                    }
                    row == 6 && col == 2 -> {
                        LanthanideActinidePlaceholder(
                            isLanthanide = false,
                            isDimmed = highlightedCategory != null && highlightedCategory != ElementCategory.ACTINIDE
                        )
                    }
                    else -> {
                        EmptyElementCell()
                    }
                }
            }
        }
    }
}
