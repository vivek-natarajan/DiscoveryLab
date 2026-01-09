package com.example.discoverylab.content.periodictable.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a chemical element with comprehensive properties.
 * Data sourced from Bowserinator/Periodic-Table-JSON and komed3/periodic-table.
 */
@Serializable
data class Element(
    // === IDENTIFICATION ===
    val atomicNumber: Int,
    val symbol: String,
    val name: String,
    val latinName: String? = null,

    // === ATOMIC PROPERTIES ===
    val atomicMass: Double,
    val standardAtomicWeight: String? = null,

    // === CLASSIFICATION ===
    val category: ElementCategory,
    val group: Int? = null,  // null for lanthanides/actinides
    val period: Int,
    val block: String = "s",  // s, p, d, f

    // === ELECTRON STRUCTURE ===
    val electronConfiguration: String = "",
    val electronConfigurationSemantic: String = "",
    val electronShells: List<Int> = emptyList(),

    // === ELECTRONEGATIVITY (Multiple Scales) ===
    val electronegativity: Double? = null,
    val electronegativityPauling: Double? = null,
    val electronegativitySanderson: Double? = null,
    val electronegativityAllredRochow: Double? = null,
    val electronegativityMulliken: Double? = null,
    val electronegativityAllen: Double? = null,

    // === IONIZATION ENERGIES ===
    val ionizationEnergies: List<Double> = emptyList(),
    val electronAffinity: Double? = null,

    // === ATOMIC RADII (in pm) ===
    val atomicRadiusEmpirical: Double? = null,
    val atomicRadiusCalculated: Double? = null,
    val covalentRadius: Double? = null,
    val vanDerWaalsRadius: Double? = null,

    // === PHYSICAL PROPERTIES ===
    val naturalState: StateOfMatter = StateOfMatter.UNKNOWN,
    val density: Double? = null,  // g/cm³
    val meltingPoint: Double? = null,  // Kelvin
    val boilingPoint: Double? = null,  // Kelvin
    val heatOfFusion: Double? = null,  // kJ/mol
    val heatOfVaporization: Double? = null,  // kJ/mol
    val molarHeatCapacity: Double? = null,  // J/(mol·K)
    val specificHeatCapacity: Double? = null,  // J/(kg·K)

    // === APPEARANCE ===
    val appearance: String? = null,

    // === THERMAL PROPERTIES ===
    val thermalConductivity: Double? = null,  // W/(m·K)
    val thermalExpansion: Double? = null,  // K⁻¹

    // === ELECTRICAL PROPERTIES ===
    val electricalConductivity: Double? = null,  // S/m
    val superconductingPoint: Double? = null,  // K

    // === MAGNETIC PROPERTIES ===
    val magneticOrdering: String? = null,
    val magneticSusceptibility: Double? = null,

    // === MECHANICAL PROPERTIES ===
    val mohsHardness: Double? = null,
    val youngsModulus: Double? = null,  // GPa
    val shearModulus: Double? = null,  // GPa
    val bulkModulus: Double? = null,  // GPa
    val poissonRatio: Double? = null,
    val soundSpeed: Double? = null,  // m/s

    // === OPTICAL PROPERTIES ===
    val refractiveIndex: Double? = null,

    // === CRYSTAL STRUCTURE ===
    val crystalStructure: String? = null,

    // === CHEMICAL PROPERTIES ===
    val oxidationStates: List<Int> = emptyList(),
    val standardElectrodePotential: Double? = null,  // V
    val basicity: String? = null,

    // === DISCOVERY ===
    val discoveredBy: String? = null,
    val namedBy: String? = null,
    val discoveryYear: Int? = null,
    val era: String? = null,  // Historical period (e.g., "antiquity", "1800s")
    val nameOrigin: String? = null,

    // === ABUNDANCE (ppb) ===
    val abundanceUniverse: Double? = null,
    val abundanceSun: Double? = null,
    val abundanceMeteor: Double? = null,
    val abundanceCrust: Double? = null,
    val abundanceOcean: Double? = null,
    val abundanceHuman: Double? = null,

    // === CLASSIFICATION IDS ===
    val casNumber: String? = null,
    val pubchemCid: Int? = null,
    val rtecsNumber: String? = null,

    // === HAZARD DATA ===
    val ghsSymbols: List<String> = emptyList(),
    val hStatements: List<String> = emptyList(),
    val pStatements: List<String> = emptyList(),
    val nfpaHealth: Int? = null,
    val nfpaFire: Int? = null,
    val nfpaReactivity: Int? = null,

    // === PRICE ===
    val pricePerKg: Double? = null,

    // === IMAGES & 3D MODELS ===
    val imageUrl: String? = null,
    val imageTitle: String? = null,
    val imageAttribution: String? = null,
    val bohrModelImage: String? = null,
    val bohrModel3d: String? = null,  // GLB 3D model URL
    val spectralImage: String? = null,

    // === TEXT CONTENT ===
    val summary: String? = null,
    val wikipediaUrl: String? = null,

    // === CPK COLOR ===
    val cpkHexColor: String? = null,

    // === ISOTOPES ===
    val isotopes: List<Isotope> = emptyList(),

    // === LEGACY FIELDS (backward compatibility) ===
    val trivia: List<String> = emptyList(),
    val realWorldUses: List<String> = emptyList(),
    val whereFound: List<String> = emptyList()
) {
    // Computed properties for convenience
    val protons: Int get() = atomicNumber
    val electrons: Int get() = atomicNumber  // Neutral atom
    val neutrons: Int get() = (atomicMass.toInt() - atomicNumber).coerceAtLeast(0)
    val valenceElectrons: Int get() = electronShells.lastOrNull() ?: 0
}

/**
 * Isotope data for an element.
 */
@Serializable
data class Isotope(
    val massNumber: Int,
    val neutrons: Int,
    val atomicMass: Double? = null,
    val abundance: Double = 0.0,
    val halfLife: String? = null,  // String to handle "?" for unknown values
    val halfLifeUnit: String? = null,
    val decayModes: List<String> = emptyList(),
    val spin: String? = null,
    val stable: Boolean = false,
    val bindingEnergy: Double? = null,
    val magneticDipole: Double? = null
)

/**
 * Categories of elements in the periodic table.
 */
@Serializable
enum class ElementCategory(val displayName: String) {
    ALKALI_METAL("Alkali Metal"),
    ALKALINE_EARTH_METAL("Alkaline Earth Metal"),
    TRANSITION_METAL("Transition Metal"),
    POST_TRANSITION_METAL("Post-Transition Metal"),
    METALLOID("Metalloid"),
    NONMETAL("Nonmetal"),
    HALOGEN("Halogen"),
    NOBLE_GAS("Noble Gas"),
    LANTHANIDE("Lanthanide"),
    ACTINIDE("Actinide"),
    UNKNOWN("Unknown")
}

/**
 * Physical states of matter at standard temperature and pressure.
 */
@Serializable
enum class StateOfMatter(val displayName: String) {
    SOLID("Solid"),
    LIQUID("Liquid"),
    GAS("Gas"),
    UNKNOWN("Unknown")
}

/**
 * Returns the state of matter at a given temperature in Kelvin.
 */
fun Element.stateAtTemperature(tempKelvin: Double): StateOfMatter {
    val melting = meltingPoint ?: return StateOfMatter.UNKNOWN
    val boiling = boilingPoint ?: return StateOfMatter.UNKNOWN

    return when {
        tempKelvin < melting -> StateOfMatter.SOLID
        tempKelvin < boiling -> StateOfMatter.LIQUID
        else -> StateOfMatter.GAS
    }
}

/**
 * Grid position for rendering in the periodic table.
 * Standard periodic table layout: 18 columns x 10 rows (including lanthanide/actinide rows)
 */
@Serializable
data class GridPosition(
    val row: Int,  // 0-indexed, 0-6 for main table, 8-9 for lanthanides/actinides
    val column: Int  // 0-indexed, 0-17
)

/**
 * Returns the grid position for this element in the standard periodic table layout.
 */
fun Element.gridPosition(): GridPosition {
    return when {
        // Lanthanides (57-71) go in row 8
        atomicNumber in 57..71 -> GridPosition(row = 8, column = atomicNumber - 57 + 3)
        // Actinides (89-103) go in row 9
        atomicNumber in 89..103 -> GridPosition(row = 9, column = atomicNumber - 89 + 3)
        // Period 1
        atomicNumber == 1 -> GridPosition(row = 0, column = 0)
        atomicNumber == 2 -> GridPosition(row = 0, column = 17)
        // Period 2
        atomicNumber in 3..4 -> GridPosition(row = 1, column = atomicNumber - 3)
        atomicNumber in 5..10 -> GridPosition(row = 1, column = atomicNumber - 5 + 12)
        // Period 3
        atomicNumber in 11..12 -> GridPosition(row = 2, column = atomicNumber - 11)
        atomicNumber in 13..18 -> GridPosition(row = 2, column = atomicNumber - 13 + 12)
        // Period 4
        atomicNumber in 19..36 -> GridPosition(row = 3, column = atomicNumber - 19)
        // Period 5
        atomicNumber in 37..54 -> GridPosition(row = 4, column = atomicNumber - 37)
        // Period 6 (excluding lanthanides)
        atomicNumber == 55 -> GridPosition(row = 5, column = 0)
        atomicNumber == 56 -> GridPosition(row = 5, column = 1)
        atomicNumber in 72..86 -> GridPosition(row = 5, column = atomicNumber - 72 + 3)
        // Period 7 (excluding actinides)
        atomicNumber == 87 -> GridPosition(row = 6, column = 0)
        atomicNumber == 88 -> GridPosition(row = 6, column = 1)
        atomicNumber in 104..118 -> GridPosition(row = 6, column = atomicNumber - 104 + 3)
        // Elements beyond 118 are not displayed in the standard table
        atomicNumber > 118 -> GridPosition(row = -1, column = -1)
        else -> GridPosition(row = 0, column = 0)
    }
}
