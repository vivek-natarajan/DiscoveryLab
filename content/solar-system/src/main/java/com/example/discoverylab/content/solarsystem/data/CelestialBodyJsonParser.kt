package com.example.discoverylab.content.solarsystem.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Wrapper for the old celestial_bodies.json file structure (kept for compatibility).
 */
@Serializable
data class CelestialBodiesJsonFile(
    val version: Int,
    val generated: String? = null,
    val sources: List<String> = emptyList(),
    val bodies: List<CelestialBody>
)

/**
 * Parser for loading celestial body data from JSON.
 */
object CelestialBodyJsonParser {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Parse a single celestial body from JSON string (individual file format).
     */
    fun parseSingle(jsonString: String): CelestialBody {
        return json.decodeFromString<CelestialBody>(jsonString)
    }

    /**
     * Parse celestial bodies from JSON string (old wrapped format).
     */
    fun parse(jsonString: String): List<CelestialBody> {
        val file = json.decodeFromString<CelestialBodiesJsonFile>(jsonString)
        return file.bodies
    }
}
