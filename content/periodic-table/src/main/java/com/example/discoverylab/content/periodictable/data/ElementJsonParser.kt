package com.example.discoverylab.content.periodictable.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Wrapper for the elements.json file structure.
 */
@Serializable
data class ElementsJsonFile(
    val version: Int,
    val generated: String? = null,
    val sources: List<String> = emptyList(),
    val elements: List<Element>
)

/**
 * Parser for loading element data from JSON.
 */
object ElementJsonParser {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Parse elements from JSON string.
     */
    fun parse(jsonString: String): List<Element> {
        val file = json.decodeFromString<ElementsJsonFile>(jsonString)
        return file.elements
    }
}
