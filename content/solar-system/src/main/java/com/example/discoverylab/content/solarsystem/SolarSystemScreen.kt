package com.example.discoverylab.content.solarsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.discoverylab.content.solarsystem.data.CelestialBody
import com.example.discoverylab.content.solarsystem.data.CelestialBodyRepository
import com.example.discoverylab.content.solarsystem.ui.detail.FullScreenDetailView
import com.example.discoverylab.content.solarsystem.ui.home.HomeView

/**
 * Main screen for the Solar System Explorer.
 * Shows solar system image with Sun info by default, detail view when planet selected.
 */
@Composable
fun SolarSystemScreen() {
    val context = LocalContext.current

    // Load celestial bodies
    var bodies by remember { mutableStateOf<List<CelestialBody>>(emptyList()) }
    LaunchedEffect(Unit) {
        bodies = CelestialBodyRepository.loadBodies(context)
    }

    // null means showing home view with Sun info
    var selectedBodyId by remember { mutableStateOf<String?>(null) }
    val selectedBody = selectedBodyId?.let { id -> bodies.find { it.id == id } }
    val sunBody = bodies.find { it.id == "sun" }

    if (selectedBody != null) {
        // Full-screen detail view for selected planet
        FullScreenDetailView(
            body = selectedBody,
            allBodies = bodies,
            context = context,
            onBack = { selectedBodyId = null },
            onNavigate = { newId -> selectedBodyId = newId }
        )
    } else {
        // Home view: Solar system image + Sun info
        HomeView(
            context = context,
            bodies = bodies,
            sunBody = sunBody,
            onBodySelected = { id -> selectedBodyId = id }
        )
    }
}
