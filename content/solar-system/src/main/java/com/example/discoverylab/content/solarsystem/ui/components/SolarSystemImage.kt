package com.example.discoverylab.content.solarsystem.ui.components

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.discoverylab.content.solarsystem.ui.theme.SolarColors

/**
 * Planet hotspot positions as fractions of image dimensions (0-1).
 */
internal data class PlanetHotspot(
    val id: String,
    val x: Float,
    val y: Float,
    val radius: Float
)

// Hotspots computed from bounding boxes
private val planetHotspots = listOf(
    PlanetHotspot("sun", 0.105f, 0.508f, 0.12f),
    PlanetHotspot("mercury", 0.230f, 0.582f, 0.04f),
    PlanetHotspot("venus", 0.285f, 0.580f, 0.05f),
    PlanetHotspot("earth", 0.357f, 0.477f, 0.06f),
    PlanetHotspot("mars", 0.413f, 0.553f, 0.05f),
    PlanetHotspot("jupiter", 0.519f, 0.450f, 0.08f),
    PlanetHotspot("saturn", 0.676f, 0.349f, 0.12f),
    PlanetHotspot("uranus", 0.807f, 0.264f, 0.06f),
    PlanetHotspot("neptune", 0.912f, 0.187f, 0.06f)
)

// Image aspect ratio (1024 x 559)
private const val IMAGE_ASPECT_RATIO = 1024f / 559f

/**
 * Calculates the actual image bounds within a container when using FillWidth scaling.
 */
private fun calculateImageBounds(containerSize: IntSize): Pair<Float, Float> {
    if (containerSize == IntSize.Zero) return Pair(0f, 0f)

    val containerWidth = containerSize.width.toFloat()
    val containerHeight = containerSize.height.toFloat()

    val imageHeight = containerWidth / IMAGE_ASPECT_RATIO
    val verticalOffset = (containerHeight - imageHeight) / 2f

    return Pair(verticalOffset.coerceAtLeast(0f), imageHeight)
}

/**
 * Finds which planet was tapped based on tap coordinates.
 */
private fun findTappedPlanet(
    tapOffset: Offset,
    containerSize: IntSize,
    imageOffsetY: Float,
    imageHeight: Float
): String? {
    if (containerSize == IntSize.Zero || imageHeight <= 0) return null

    val imageWidth = containerSize.width.toFloat()

    val tapXInImage = tapOffset.x / imageWidth
    val tapYInImage = (tapOffset.y - imageOffsetY) / imageHeight

    if (tapYInImage < 0 || tapYInImage > 1) return null

    return planetHotspots
        .filter { hotspot ->
            val dx = tapXInImage - hotspot.x
            val dy = tapYInImage - hotspot.y
            val distance = kotlin.math.sqrt(dx * dx + dy * dy)
            distance <= hotspot.radius * 1.5f
        }
        .minByOrNull { hotspot ->
            val dx = tapXInImage - hotspot.x
            val dy = tapYInImage - hotspot.y
            dx * dx + dy * dy
        }
        ?.id
}

/**
 * Displays the solar system image with tappable planet hotspots.
 */
@Composable
fun SolarSystemImage(
    context: Context,
    selectedBodyId: String?,
    onBodySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    val bitmap = remember {
        context.assets.open("solar_system.png").use { inputStream ->
            BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
        }
    }

    val (imageOffsetY, imageHeight) = calculateImageBounds(containerSize)
    val imageWidth = containerSize.width.toFloat()

    Box(
        modifier = modifier
            .onSizeChanged { containerSize = it }
            .pointerInput(containerSize) {
                detectTapGestures { offset ->
                    val tappedPlanet = findTappedPlanet(
                        tapOffset = offset,
                        containerSize = containerSize,
                        imageOffsetY = imageOffsetY,
                        imageHeight = imageHeight
                    )
                    if (tappedPlanet != null) {
                        onBodySelected(tappedPlanet)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it,
                contentDescription = "Solar System",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Selection indicator overlay
        if (containerSize != IntSize.Zero && imageHeight > 0 && selectedBodyId != null) {
            val hotspot = planetHotspots.find { it.id == selectedBodyId }
            hotspot?.let {
                val density = LocalContext.current.resources.displayMetrics.density

                val xInImage = it.x * imageWidth
                val yInImage = it.y * imageHeight
                val size = it.radius * imageWidth * 2.5f

                val xInContainer = xInImage
                val yInContainer = imageOffsetY + yInImage

                Box(
                    modifier = Modifier
                        .padding(
                            start = ((xInContainer - size / 2).coerceAtLeast(0f) / density).dp,
                            top = ((yInContainer - size / 2).coerceAtLeast(0f) / density).dp
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size((size / density).dp)
                            .clip(CircleShape)
                            .background(
                                SolarColors.forBody(selectedBodyId).copy(alpha = 0.3f)
                            )
                    )
                }
            }
        }
    }
}
