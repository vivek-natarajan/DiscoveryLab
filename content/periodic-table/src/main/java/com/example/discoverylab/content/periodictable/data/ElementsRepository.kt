package com.example.discoverylab.content.periodictable.data

import android.content.Context

/**
 * Repository providing all 118 elements with their properties.
 * Data loaded from assets/elements.json at runtime.
 * Data sourced from: Bowserinator/Periodic-Table-JSON + komed3/periodic-table
 */
object ElementsRepository {

    private var _elements: List<Element>? = null

    /**
     * Get all elements. Must call loadElements() first with a Context.
     * @throws IllegalStateException if loadElements() hasn't been called
     */
    val elements: List<Element>
        get() = _elements ?: throw IllegalStateException(
            "Elements not loaded. Call loadElements(context) first."
        )

    /**
     * Load elements from the JSON asset file.
     * Safe to call multiple times - will only load once.
     * @param context Android context to access assets
     * @return List of all 118 elements
     */
    fun loadElements(context: Context): List<Element> {
        if (_elements == null) {
            val jsonString = context.assets
                .open("elements.json")
                .bufferedReader()
                .use { it.readText() }
            _elements = ElementJsonParser.parse(jsonString)
        }
        return _elements!!
    }

    /**
     * Check if elements have been loaded.
     */
    fun isLoaded(): Boolean = _elements != null

    /**
     * Get element by atomic number.
     * @param atomicNumber Atomic number (1-118)
     * @return Element or null if not found
     */
    fun getElement(atomicNumber: Int): Element? =
        elements.find { it.atomicNumber == atomicNumber }

    /**
     * Get element by symbol.
     * @param symbol Chemical symbol (case-insensitive)
     * @return Element or null if not found
     */
    fun getElement(symbol: String): Element? =
        elements.find { it.symbol.equals(symbol, ignoreCase = true) }

    /**
     * Get all elements in a category.
     * @param category Element category
     * @return List of elements in that category
     */
    fun getElementsByCategory(category: ElementCategory): List<Element> =
        elements.filter { it.category == category }

    /**
     * Get all elements in a block.
     * @param block Block (s, p, d, f)
     * @return List of elements in that block
     */
    fun getElementsByBlock(block: String): List<Element> =
        elements.filter { it.block.equals(block, ignoreCase = true) }

    /**
     * Get all elements in a period.
     * @param period Period number (1-7)
     * @return List of elements in that period
     */
    fun getElementsByPeriod(period: Int): List<Element> =
        elements.filter { it.period == period }

    /**
     * Get all elements in a group.
     * @param group Group number (1-18)
     * @return List of elements in that group
     */
    fun getElementsByGroup(group: Int): List<Element> =
        elements.filter { it.group == group }
}
