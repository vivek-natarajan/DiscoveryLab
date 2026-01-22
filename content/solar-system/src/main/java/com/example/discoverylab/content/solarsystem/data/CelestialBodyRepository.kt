package com.example.discoverylab.content.solarsystem.data

import android.content.Context

/**
 * Repository providing celestial body data for the Solar System Explorer.
 * Data loaded from individual JSON files in assets folder.
 */
object CelestialBodyRepository {

    private var _bodies: List<CelestialBody>? = null

    // List of celestial body JSON files to load
    private val BODY_FILES = listOf(
        "sun.json",
        "mercury.json",
        "venus.json",
        "earth.json",
        "moon.json",
        "mars.json",
        "jupiter.json",
        "saturn.json",
        "uranus.json",
        "neptune.json"
    )

    /**
     * Get all celestial bodies. Must call loadBodies() first with a Context.
     * @throws IllegalStateException if loadBodies() hasn't been called
     */
    val bodies: List<CelestialBody>
        get() = _bodies ?: throw IllegalStateException(
            "Celestial bodies not loaded. Call loadBodies(context) first."
        )

    /**
     * Load celestial bodies from individual JSON asset files.
     * Safe to call multiple times - will only load once.
     * @param context Android context to access assets
     * @return List of all celestial bodies
     */
    fun loadBodies(context: Context): List<CelestialBody> {
        if (_bodies == null) {
            val loadedBodies = mutableListOf<CelestialBody>()

            for (fileName in BODY_FILES) {
                try {
                    val jsonString = context.assets
                        .open(fileName)
                        .bufferedReader()
                        .use { it.readText() }
                    val body = CelestialBodyJsonParser.parseSingle(jsonString)
                    loadedBodies.add(body)
                } catch (e: Exception) {
                    // Log error but continue loading other files
                    android.util.Log.e("CelestialBodyRepository", "Failed to load $fileName: ${e.message}")
                }
            }

            _bodies = loadedBodies.sortedBy { it.orderFromSun }
        }
        return _bodies!!
    }

    /**
     * Check if bodies have been loaded.
     */
    fun isLoaded(): Boolean = _bodies != null

    /**
     * Get celestial body by ID.
     * @param id Body ID (e.g., "earth", "moon", "sun")
     * @return CelestialBody or null if not found
     */
    fun getBody(id: String): CelestialBody? =
        bodies.find { it.id.equals(id, ignoreCase = true) }

    /**
     * Get celestial body by name.
     * @param name Body name (case-insensitive)
     * @return CelestialBody or null if not found
     */
    fun getBodyByName(name: String): CelestialBody? =
        bodies.find { it.name.equals(name, ignoreCase = true) }

    /**
     * Get all bodies of a specific type.
     * @param type CelestialBodyType
     * @return List of bodies of that type
     */
    fun getBodiesByType(type: CelestialBodyType): List<CelestialBody> =
        bodies.filter { it.type == type }

    /**
     * Get all planets (ordered by distance from Sun).
     */
    fun getPlanets(): List<CelestialBody> =
        bodies.filter { it.isPlanet }.sortedBy { it.orderFromSun }

    /**
     * Get the Sun.
     */
    fun getSun(): CelestialBody? =
        bodies.find { it.isStar }

    /**
     * Get all moons.
     */
    fun getMoons(): List<CelestialBody> =
        bodies.filter { it.isMoon }

    /**
     * Get moons of a specific planet.
     * @param planetId ID of the parent planet
     * @return List of moons orbiting that planet
     */
    fun getMoonsOf(planetId: String): List<CelestialBody> =
        bodies.filter { it.isMoon && it.parentId == planetId }

    /**
     * Get all bodies sorted by order from Sun.
     */
    fun getBodiesSortedByDistance(): List<CelestialBody> =
        bodies.sortedBy { it.orderFromSun }

    /**
     * Get body at specific index (for navigation).
     */
    fun getBodyAtIndex(index: Int): CelestialBody? =
        bodies.getOrNull(index)

    /**
     * Get index of a body (for navigation).
     */
    fun getIndexOf(body: CelestialBody): Int =
        bodies.indexOf(body)
}
