package com.example.discoverylab.content.solarsystem.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull

/**
 * Types of celestial bodies in the solar system.
 */
enum class CelestialBodyType(val displayName: String) {
    STAR("Star"),
    PLANET("Planet"),
    DWARF_PLANET("Dwarf Planet"),
    MOON("Moon"),
    ASTEROID("Asteroid"),
    COMET("Comet");

    companion object {
        fun fromString(value: String?): CelestialBodyType = when (value?.lowercase()) {
            "star" -> STAR
            "terrestrial", "gas-giant", "ice-giant", "planet" -> PLANET
            "dwarf-planet" -> DWARF_PLANET
            "natural-satellite", "moon" -> MOON
            "asteroid" -> ASTEROID
            "comet" -> COMET
            else -> PLANET
        }
    }
}

/**
 * Represents a celestial body with comprehensive properties.
 * Supports flexible schema from NASA/Grokipedia data.
 */
@Serializable
data class CelestialBody(
    val id: String,
    val name: String,
    val alternativeNames: List<String> = emptyList(),

    // Type - can come from different fields
    val planetType: String? = null,
    val celestialType: String? = null,

    // Order from sun
    val order: Int? = null,

    // Parent body (for moons)
    val parentPlanet: String? = null,

    // Spectral class (for stars)
    val spectralClass: String? = null,

    // Descriptions at different depths
    val descriptions: Descriptions? = null,

    // Fun facts
    val funFacts: List<String> = emptyList(),

    // Physical characteristics
    val physicalCharacteristics: PhysicalCharacteristics? = null,

    // Orbital characteristics
    val orbitalCharacteristics: OrbitalCharacteristics? = null,

    // Temperature (for sun - separate from physical)
    val temperature: SunTemperature? = null,

    // Structure (for sun)
    val structure: SunStructure? = null,

    // Composition (for sun)
    val composition: SunComposition? = null,

    // Rotation (for sun)
    val rotation: SunRotation? = null,

    // Solar activity (for sun)
    val solarActivity: SolarActivity? = null,

    // Lifespan (for sun)
    val lifespan: Lifespan? = null,

    // Atmosphere
    val atmosphere: Atmosphere? = null,

    // Surface
    val surface: Surface? = null,

    // Internal structure (for moon)
    val internalStructure: InternalStructure? = null,

    // Formation (for moon)
    val formation: Formation? = null,

    // Phases (for moon)
    val phases: Phases? = null,

    // Eclipses (for moon)
    val eclipses: Eclipses? = null,

    // Moons (new schema with majorMoons/minorMoons)
    val moons: MoonsData? = null,

    // Rings (for Saturn)
    val rings: RingsData? = null,

    // Mythology
    val mythology: Mythology? = null,

    // History
    val history: History? = null,

    // Missions
    val missions: List<Mission> = emptyList(),

    // Exploration
    val exploration: Exploration? = null,

    // Observation from Earth (for sun)
    val observationFromEarth: ObservationFromEarth? = null,

    // Comparison to Earth
    val comparisonToEarth: ComparisonToEarth? = null,

    // Interesting facts
    val interestingFacts: Map<String, String> = emptyMap(),

    // Metadata
    val metadata: Metadata? = null
) {
    // Computed properties
    val type: CelestialBodyType
        get() = CelestialBodyType.fromString(celestialType ?: planetType)

    val orderFromSun: Int
        get() = order ?: when (id.lowercase()) {
            "sun" -> 0
            "mercury" -> 1
            "venus" -> 2
            "earth" -> 3
            "moon" -> 3  // Same as Earth for positioning
            "mars" -> 4
            "jupiter" -> 5
            "saturn" -> 6
            "uranus" -> 7
            "neptune" -> 8
            else -> 99
        }

    val shortDescription: String
        get() = descriptions?.short ?: ""

    val mediumDescription: String
        get() = descriptions?.medium ?: shortDescription

    val longDescription: String
        get() = descriptions?.long ?: mediumDescription

    val surfaceGravity: Double
        get() = physicalCharacteristics?.gravity?.surface ?: physicalCharacteristics?.surfaceGravity?.value ?: 0.0

    val gravityRatio: Double
        get() = surfaceGravity / EARTH_GRAVITY

    val diameter: Double
        get() = physicalCharacteristics?.diameter?.value ?: (physicalCharacteristics?.radius?.mean ?: 0.0) * 2

    val meanTemperature: Double
        get() = physicalCharacteristics?.temperature?.mean
            ?: physicalCharacteristics?.temperature?.kelvin
            ?: temperature?.photosphere?.value?.toDouble()
            ?: 0.0

    val isStar: Boolean get() = type == CelestialBodyType.STAR
    val isPlanet: Boolean get() = type == CelestialBodyType.PLANET
    val isDwarfPlanet: Boolean get() = type == CelestialBodyType.DWARF_PLANET
    val isMoon: Boolean get() = type == CelestialBodyType.MOON

    val parentId: String?
        get() = parentPlanet?.lowercase()

    val moonCount: Int
        get() = moons?.total ?: (moons?.majorMoons?.size ?: 0) + (moons?.minorMoons?.size ?: 0)

    val hasRings: Boolean
        get() = id.lowercase() in listOf("saturn", "jupiter", "uranus", "neptune")

    val distanceFromSun: Double
        get() = orbitalCharacteristics?.distanceFromSun?.semimajorAxis
            ?: orbitalCharacteristics?.distanceFromEarth?.mean
            ?: 0.0

    companion object {
        const val EARTH_GRAVITY = 9.8
    }
}

@Serializable
data class Descriptions(
    val short: String? = null,
    val medium: String? = null,
    val long: String? = null
)

@Serializable
data class PhysicalCharacteristics(
    val mass: MassInfo? = null,
    val volume: VolumeInfo? = null,
    val radius: RadiusInfo? = null,
    val diameter: DiameterInfo? = null,
    val density: DensityInfo? = null,
    val gravity: GravityInfo? = null,
    val surfaceGravity: SurfaceGravityInfo? = null,
    val escapeVelocity: EscapeVelocityInfo? = null,
    val temperature: TemperatureInfo? = null,
    val luminosity: LuminosityInfo? = null
)

@Serializable
data class MassInfo(
    val value: Double,
    val unit: String? = null,
    val earthComparison: Double? = null
)

@Serializable
data class VolumeInfo(
    val value: Double,
    val unit: String? = null,
    val earthComparison: Double? = null
)

@Serializable
data class RadiusInfo(
    val value: Double? = null,
    val equatorial: Double? = null,
    val polar: Double? = null,
    val mean: Double? = null,
    val unit: String? = null,
    val earthComparison: Double? = null
)

@Serializable
data class DiameterInfo(
    val value: Double,
    val unit: String? = null,
    val earthComparison: Double? = null
)

@Serializable
data class DensityInfo(
    val value: Double,
    val unit: String? = null,
    val description: String? = null
)

@Serializable
data class GravityInfo(
    val surface: Double,
    val unit: String? = null,
    val earthComparison: Double? = null
)

@Serializable
data class SurfaceGravityInfo(
    val value: Double,
    val unit: String? = null,
    val earthComparison: Double? = null
)

@Serializable
data class EscapeVelocityInfo(
    val value: Double,
    val unit: String? = null
)

@Serializable
data class TemperatureInfo(
    val mean: Double? = null,
    val unit: String? = null,
    val kelvin: Double? = null,
    val range: String? = null,
    val description: String? = null
)

@Serializable
data class LuminosityInfo(
    val value: Double,
    val unit: String? = null
)

@Serializable
data class OrbitalCharacteristics(
    val distanceFromSun: DistanceInfo? = null,
    val distanceFromEarth: DistanceFromEarthInfo? = null,
    val orbitalPeriod: OrbitalPeriodInfo? = null,
    val orbitalVelocity: OrbitalVelocityInfo? = null,
    val orbitalEccentricity: Double? = null,
    val orbitalInclination: InclinationInfo? = null,
    val rotationPeriod: RotationPeriodInfo? = null,
    val lengthOfDay: LengthOfDayInfo? = null,
    val axialTilt: AxialTiltInfo? = null,
    val tidalLocking: TidalLockingInfo? = null
)

@Serializable
data class DistanceInfo(
    val semimajorAxis: Double? = null,
    val perihelion: Double? = null,
    val aphelion: Double? = null,
    val unit: String? = null
)

@Serializable
data class DistanceFromEarthInfo(
    val mean: Double? = null,
    val perigee: Double? = null,
    val apogee: Double? = null,
    val unit: String? = null
)

@Serializable
data class OrbitalPeriodInfo(
    val value: Double? = null,
    val sidereal: Double? = null,
    val synodic: Double? = null,
    val unit: String? = null,
    val description: String? = null,
    val marsYears: Double? = null
)

@Serializable
data class OrbitalVelocityInfo(
    val mean: Double,
    val max: Double? = null,
    val min: Double? = null,
    val unit: String? = null
)

@Serializable
data class InclinationInfo(
    val value: Double,
    val unit: String? = null,
    val description: String? = null
)

@Serializable
data class RotationPeriodInfo(
    val value: Double,
    val unit: String? = null,
    val days: Double? = null,
    val direction: String? = null,
    val description: String? = null
)

@Serializable
data class LengthOfDayInfo(
    val value: Double,
    val unit: String? = null,
    val days: Double? = null,
    val sol: String? = null
)

@Serializable
data class AxialTiltInfo(
    val value: Double,
    val unit: String? = null,
    val description: String? = null
)

@Serializable
data class TidalLockingInfo(
    val description: String? = null,
    val consequence: String? = null
)

// Sun-specific types
@Serializable
data class SunTemperature(
    val core: TempValue? = null,
    val radiativeZone: TempValue? = null,
    val convectiveZone: TempValue? = null,
    val photosphere: TempValue? = null,
    val chromosphere: TempValue? = null,
    val corona: TempValue? = null
)

@Serializable
data class TempValue(
    val value: Int,
    val unit: String? = null,
    val description: String? = null
)

@Serializable
data class SunStructure(
    val core: StructureLayer? = null,
    val radiativeZone: StructureLayer? = null,
    val convectiveZone: StructureLayer? = null,
    val photosphere: StructureLayer? = null,
    val chromosphere: StructureLayer? = null,
    val corona: StructureLayer? = null
)

@Serializable
data class StructureLayer(
    val description: String? = null,
    val radius: String? = null,
    val thickness: String? = null,
    val extent: String? = null,
    val temperature: String? = null,
    val pressure: String? = null,
    val features: String? = null
)

@Serializable
data class SunComposition(
    val hydrogen: Double? = null,
    val helium: Double? = null,
    val oxygen: Double? = null,
    val carbon: Double? = null,
    val iron: Double? = null,
    val neon: Double? = null,
    val nitrogen: Double? = null,
    val silicon: Double? = null,
    val magnesium: Double? = null,
    val sulfur: Double? = null,
    val unit: String? = null
)

@Serializable
data class SunRotation(
    val equatorial: RotationValue? = null,
    val polar: RotationValue? = null,
    val type: String? = null
)

@Serializable
data class RotationValue(
    val value: Double,
    val unit: String? = null,
    val description: String? = null
)

@Serializable
data class SolarActivity(
    val solarCycle: SolarCycle? = null,
    val sunspots: Sunspots? = null,
    val solarFlares: SolarFlares? = null,
    val coronalMassEjections: CoronalMassEjections? = null,
    val solarWind: SolarWind? = null
)

@Serializable
data class SolarCycle(
    val period: String? = null,
    val description: String? = null
)

@Serializable
data class Sunspots(
    val description: String? = null,
    val cause: String? = null,
    val size: String? = null,
    val cycle: String? = null
)

@Serializable
data class SolarFlares(
    val description: String? = null,
    val cause: String? = null,
    val impact: String? = null
)

@Serializable
data class CoronalMassEjections(
    val description: String? = null,
    val impact: String? = null
)

@Serializable
data class SolarWind(
    val description: String? = null,
    val speed: String? = null,
    val extent: String? = null
)

@Serializable
data class Lifespan(
    val currentAge: String? = null,
    val mainSequenceLifetime: String? = null,
    val remainingLife: String? = null,
    val futureEvolution: FutureEvolution? = null
)

@Serializable
data class FutureEvolution(
    val description: String? = null,
    val stages: List<EvolutionStage> = emptyList()
)

@Serializable
data class EvolutionStage(
    val stage: String,
    val timeframe: String? = null,
    val description: String? = null
)

// Atmosphere
@Serializable
data class Atmosphere(
    val composition: List<AtmosphereComponent> = emptyList(),
    val surfacePressure: SurfacePressure? = null,
    val description: String? = null,
    val consequences: List<String> = emptyList()
)

@Serializable
data class AtmosphereComponent(
    val gas: String,
    val percentage: Double
)

/**
 * Serializer that handles values that can be either a number or a string like "> 1000"
 */
@OptIn(ExperimentalSerializationApi::class)
object FlexibleDoubleSerializer : KSerializer<Double?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FlexibleDouble", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Double?) {
        if (value != null) encoder.encodeDouble(value) else encoder.encodeNull()
    }

    override fun deserialize(decoder: Decoder): Double? {
        return try {
            val jsonDecoder = decoder as? JsonDecoder
            val element = jsonDecoder?.decodeJsonElement()
            when (element) {
                is JsonPrimitive -> element.doubleOrNull
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Serializer that handles values that can be either a string or an integer (converts int to string)
 */
@OptIn(ExperimentalSerializationApi::class)
object FlexibleStringSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FlexibleString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String?) {
        if (value != null) encoder.encodeString(value) else encoder.encodeNull()
    }

    override fun deserialize(decoder: Decoder): String? {
        return try {
            val jsonDecoder = decoder as? JsonDecoder
            val element = jsonDecoder?.decodeJsonElement()
            when (element) {
                is JsonPrimitive -> element.content
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}

@Serializable
data class SurfacePressure(
    @Serializable(with = FlexibleDoubleSerializer::class)
    val value: Double? = null,
    val valueText: String? = null,
    val unit: String? = null,
    val earthComparison: Double? = null,
    val description: String? = null
)

// Surface
@Serializable
data class Surface(
    val features: List<SurfaceFeature> = emptyList(),
    val color: String? = null,
    val terrain: String? = null,
    val composition: String? = null,
    val tectonics: String? = null,
    val regolith: Regolith? = null
)

@Serializable
data class SurfaceFeature(
    val name: String,
    val type: String? = null,
    val description: String? = null,
    val significance: String? = null
)

@Serializable
data class Regolith(
    val description: String? = null,
    val depth: String? = null,
    val characteristics: String? = null
)

// Moon-specific
@Serializable
data class InternalStructure(
    val crust: InternalLayer? = null,
    val mantle: InternalLayer? = null,
    val core: InternalLayer? = null
)

@Serializable
data class InternalLayer(
    val thickness: String? = null,
    val radius: String? = null,
    val composition: String? = null,
    val state: String? = null
)

@Serializable
data class Formation(
    val age: String? = null,
    val theory: String? = null,
    val description: String? = null
)

@Serializable
data class Phases(
    val description: String? = null,
    val cycle: String? = null,
    val phases: List<Phase> = emptyList()
)

@Serializable
data class Phase(
    val name: String,
    val description: String? = null
)

@Serializable
data class Eclipses(
    val solarEclipse: EclipseInfo? = null,
    val lunarEclipse: LunarEclipseInfo? = null
)

@Serializable
data class EclipseInfo(
    val description: String? = null,
    val types: List<String> = emptyList(),
    val frequency: String? = null,
    val rarity: String? = null
)

@Serializable
data class LunarEclipseInfo(
    val description: String? = null,
    val types: List<String> = emptyList(),
    val bloodMoon: String? = null,
    val frequency: String? = null
)

// Moons container
@Serializable
data class MoonsData(
    val total: Int? = null,
    val note: String? = null,
    val majorMoons: List<MoonInfo> = emptyList(),
    val minorMoons: List<MoonInfo> = emptyList()
) {
    val allMoons: List<MoonInfo>
        get() = majorMoons + minorMoons
}

@Serializable
data class MoonInfo(
    val name: String,
    val radius: Double? = null,
    val diameter: Double? = null,
    val orbitalPeriod: Double? = null,
    val distanceFromPlanet: Double? = null,
    @Serializable(with = FlexibleStringSerializer::class)
    val discoveryYear: String? = null,  // Handles both "Ancient" and 1610
    val discoveredBy: String? = null,
    val description: String? = null,
    val mythology: String? = null,
    val features: String? = null,
    val mass: Double? = null,
    val unit: String? = null,
    val distanceUnit: String? = null
)

// Rings (for Saturn)
@Serializable
data class RingsData(
    val description: String? = null,
    val mainRings: List<RingInfo> = emptyList(),
    val thickness: String? = null,
    val origin: String? = null
)

@Serializable
data class RingInfo(
    val name: String,
    val distance: String? = null
)

// Mythology
@Serializable
data class Mythology(
    val roman: CulturalMythology? = null,
    val greek: CulturalMythology? = null,
    val indian: IndianMythology? = null,
    val chinese: ChineseMythology? = null,
    val egyptian: EgyptianMythology? = null,
    val norse: CulturalMythology? = null,
    val aztec: CulturalMythology? = null
) {
    val primaryName: String?
        get() = roman?.name ?: greek?.name

    val primaryStory: String?
        get() = roman?.story ?: greek?.story ?: indian?.story

    val symbol: String?
        get() = null  // Could extract from data if available
}

@Serializable
data class CulturalMythology(
    val name: String? = null,
    val namedAfter: String? = null,
    val origin: String? = null,
    val greekEquivalent: String? = null,
    val story: String? = null,
    val characteristics: String? = null,
    val culturalSignificance: String? = null,
    val symbolism: String? = null,
    val connection: String? = null,
    val alternativeNames: List<String> = emptyList()
)

@Serializable
data class IndianMythology(
    val names: List<String> = emptyList(),
    val meaning: String? = null,
    val origin: String? = null,
    val birthStory: String? = null,
    val characteristics: String? = null,
    val story: String? = null,
    val role: String? = null,
    val astrologicalSignificance: String? = null,
    val culturalSignificance: String? = null,
    val symbolism: String? = null
)

@Serializable
data class ChineseMythology(
    val name: String? = null,
    val meaning: String? = null,
    val origin: String? = null,
    val element: String? = null,
    val characteristics: String? = null,
    val mythology: String? = null,
    val culturalSignificance: String? = null,
    val symbolism: String? = null,
    val philosophy: String? = null
)

@Serializable
data class EgyptianMythology(
    val name: String? = null,
    val alternativeName: String? = null,
    val alternativeNames: List<String> = emptyList(),
    val names: List<String> = emptyList(),
    val origin: String? = null,
    val characteristics: String? = null,
    val story: String? = null,
    val culturalSignificance: String? = null,
    val symbolism: String? = null,
    val thoth: EgyptianDeity? = null,
    val khonsu: EgyptianDeity? = null
)

@Serializable
data class EgyptianDeity(
    val description: String? = null,
    val characteristics: String? = null,
    val story: String? = null,
    val symbolism: String? = null
)

// History
@Serializable
data class History(
    val formation: HistoryFormation? = null,
    val geologicalHistory: String? = null
)

@Serializable
data class HistoryFormation(
    val age: String? = null,
    val description: String? = null
)

// Missions
@Serializable
data class Mission(
    val name: String,
    val agency: String? = null,
    val country: String? = null,
    val launchDate: String? = null,
    val period: String? = null,
    val status: String? = null,
    val missionType: String? = null,
    val description: String? = null,
    val majorAchievements: List<String> = emptyList()
)

// Exploration
@Serializable
data class Exploration(
    val firstFlyby: ExplorationMilestone? = null,
    val firstOrbit: ExplorationMilestone? = null,
    val firstLanding: ExplorationMilestone? = null,
    val firstImpact: ExplorationMilestone? = null,
    val firstHumans: FirstHumans? = null,
    val totalHumans: String? = null,
    val earthObservation: EarthObservation? = null,
    val currentActiveMissions: List<String> = emptyList(),
    val futureMissions: String? = null
)

@Serializable
data class ExplorationMilestone(
    val mission: String? = null,
    val date: String? = null,
    val agency: String? = null
)

@Serializable
data class FirstHumans(
    val mission: String? = null,
    val date: String? = null,
    val astronauts: List<String> = emptyList(),
    val agency: String? = null
)

@Serializable
data class EarthObservation(
    val description: String? = null,
    val majorPrograms: List<String> = emptyList()
)

// Observation from Earth (for sun)
@Serializable
data class ObservationFromEarth(
    val apparentMagnitude: Double? = null,
    val angularDiameter: String? = null,
    val distanceFromEarth: DistanceFromEarthObservation? = null,
    val safetyWarning: String? = null
)

@Serializable
data class DistanceFromEarthObservation(
    val mean: Double? = null,
    val unit: String? = null,
    val lightTime: String? = null
)

// Comparison to Earth
@Serializable
data class ComparisonToEarth(
    val size: String? = null,
    val mass: String? = null,
    val gravity: String? = null,
    val dayLength: String? = null,
    val yearLength: String? = null,
    val temperature: String? = null,
    val atmosphere: String? = null,
    val moons: String? = null,
    val distance: String? = null,
    val composition: String? = null,
    val energy: String? = null
)

// Metadata
@Serializable
data class Metadata(
    val dataSource: String? = null,
    val lastUpdated: String? = null,
    val version: String? = null,
    val references: List<String> = emptyList()
)
