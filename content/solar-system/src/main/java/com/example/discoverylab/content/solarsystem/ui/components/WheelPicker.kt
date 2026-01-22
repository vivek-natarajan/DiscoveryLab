package com.example.discoverylab.content.solarsystem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.solarsystem.ui.theme.SolarColors

/**
 * A reusable vertical wheel picker component with iOS-style snapping behavior.
 *
 * @param items List of items to display
 * @param selectedIndex Currently selected index
 * @param onSelectionChange Callback when selection changes
 * @param itemToString Function to convert item to display string
 * @param modifier Modifier for the picker container
 * @param width Width of the picker
 * @param height Height of the picker
 */
@Composable
fun <T> WheelPicker(
    items: List<T>,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit,
    itemToString: (T) -> String,
    modifier: Modifier = Modifier,
    width: Dp = 80.dp,
    height: Dp = 72.dp
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex.coerceAtLeast(0))
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Update selection when scroll settles
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val centerIndex = listState.firstVisibleItemIndex
            if (centerIndex in items.indices && centerIndex != selectedIndex) {
                onSelectionChange(centerIndex)
            }
        }
    }

    Box(
        modifier = modifier
            .width(width)
            .height(height)
    ) {
        // Top gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            SolarColors.background,
                            SolarColors.background.copy(alpha = 0f)
                        )
                    )
                )
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            flingBehavior = snapFlingBehavior
        ) {
            item { Spacer(modifier = Modifier.height(24.dp)) }

            itemsIndexed(items) { index, item ->
                val isSelected = index == selectedIndex
                Text(
                    text = itemToString(item),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) SolarColors.textPrimary else SolarColors.textSecondary.copy(alpha = 0.4f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .clickable { onSelectionChange(index) }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }

        // Bottom gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            SolarColors.background.copy(alpha = 0f),
                            SolarColors.background
                        )
                    )
                )
        )
    }
}
