# Codebase Context

This file captures the current state of the codebase to enable efficient navigation and reduce redundant exploration. Update this file whenever significant changes are made.

**Last Updated:** 2026-01-08

---

## Module Overview

```
DiscoveryLab/
├── app/                          # Shell application
├── core/ui/                      # Shared design system
├── content/periodic-table/       # Interactive periodic table module
└── build-logic/convention/       # Build configuration
```

---

## Content: Periodic Table Module

**Path:** `content/periodic-table/src/main/java/com/example/discoverylab/content/periodictable/`

### File Structure

```
periodictable/
├── PeriodicTableScreen.kt        # Main screen with grid + legend
├── data/
│   ├── Element.kt                # Element data model + @Serializable
│   ├── ElementCategory.kt        # Enum for element categories
│   ├── ElementSections.kt        # Progressive depth section models
│   ├── ElementsRepository.kt     # Loads elements from JSON assets
│   ├── ElementJsonParser.kt      # NEW: kotlinx.serialization JSON parser
│   ├── GridPosition.kt           # Grid positioning logic
│   └── StateOfMatter.kt          # Solid/Liquid/Gas enum
├── ui/
│   ├── PeriodicTableGrid.kt      # Grid layout (18x10)
│   ├── ElementDetailPanel.kt     # 6-section detail panel with Coil images
│   ├── ElementDetailScreen.kt    # Full-screen element details (legacy)
│   └── components/
│       └── ElementCard.kt        # Individual element card + colors
└── assets/
    └── elements.json             # All 118 elements data (267KB)
```

---

### Key Files Detail

#### PeriodicTableScreen.kt
**Purpose:** Main entry point, orchestrates grid and legend

**Key State:**
- `selectedElement: Element?` - Currently selected element (shows detail screen)
- `selectedCategory: ElementCategory?` - Category filter for highlighting
- `isLandscape: Boolean` - Orientation toggle state

**Layout Structure:**
```
Box(fillMaxSize) {
    Column {
        PeriodicTableGrid(weight=1f)      // Fills available space
        Column(bottom section) {
            IconButton(bottom left)       // Orientation toggle above legend
            InteractiveLegendBar(52dp)    // Fixed height legend
        }
    }
}
```

**Orientation Handling:**
- Landscape: No scroll, cards fill width with weight
- Portrait: Horizontal scroll, fixed 48dp card width
- Uses `DisposableEffect` to set `ActivityInfo.SCREEN_ORIENTATION_*`

---

#### ElementCard.kt
**Purpose:** Individual element display + color definitions

**Font Sizes (SansSerif):**
| Element | Size | Weight |
|---------|------|--------|
| Atomic Number | 8sp | Normal |
| Symbol | 18sp | Bold |
| Element Name | 8sp | Normal |

**Color Palette (ElementColors object):**
| Category | Color |
|----------|-------|
| Alkali Metal | #E53935 (Red) |
| Alkaline Earth | #FF7043 (Orange-red) |
| Transition Metal | #42A5F5 (Light blue) |
| Post-Transition | #26A69A (Teal) |
| Metalloid | #FFB74D (Orange) |
| Nonmetal | #4DB6AC (Cyan) |
| Noble Gas | #66BB6A (Green) |
| Lanthanide | #EC407A (Pink) |
| Actinide | #EF5350 (Red-pink) |

**Key Functions:**
- `ElementCategory.toColor()` - Returns category color
- `ElementCard()` - Composable with press animation, dimming support

---

#### PeriodicTableGrid.kt
**Purpose:** 18x10 grid layout with lanthanides/actinides separated

**Parameters:**
```kotlin
fun PeriodicTableGrid(
    elements: List<Element>,
    modifier: Modifier = Modifier,
    isLandscape: Boolean = true,
    highlightedCategory: ElementCategory? = null,
    onElementClick: (Element) -> Unit = {}
)
```

**Grid Layout:**
- Rows 0-6: Main periodic table (periods 1-7)
- Gap spacer (6dp)
- Row 8: Lanthanides (57-71)
- Row 9: Actinides (89-103)

**Scroll Behavior:**
- `isLandscape=true`: No scroll, `Modifier.weight(1f)` per card
- `isLandscape=false`: `horizontalScroll`, `Modifier.width(48.dp)` per card

---

#### ElementDetailScreen.kt
**Purpose:** Full-screen element details with organized sections

**Sections:**
1. **Header** - Name, symbol, atomic number superscript, category badge
2. **Quick Facts Row** - State/appearance/discovery year chips (FlowRow)
3. **Bohr Model** - Canvas visualization of electron shells
4. **Did You Know?** - ALL trivia items as colored cards
5. **Properties** - Table format (mass, density, melting/boiling, electronegativity, etc.)
6. **Real World** - Uses + Found In bullet lists
7. **Discovery** - Discoverer name and year in styled card

**Key Composables:**
- `SectionHeader(title, icon)` - Section divider with emoji icon
- `QuickFactChip(label, color, icon?)` - Rounded chip for quick facts
- `TriviaCard(fact, index)` - Colored card for trivia items
- `PropertyRow(label, value)` - Key-value row for properties
- `BulletItem(text)` - Bullet point for lists
- `BohrModelVisualization(electronShells, primaryColor)` - Canvas drawing

---

#### Element.kt (Data Model)
**Key Fields:**
```kotlin
data class Element(
    val atomicNumber: Int,
    val symbol: String,
    val name: String,
    val atomicMass: Double,
    val category: ElementCategory,
    val group: Int?,                    // null for lanthanides/actinides
    val period: Int,
    val block: String,                  // s, p, d, f
    val electronConfiguration: String,
    val electronShells: List<Int>,      // e.g., [2, 8, 1] for Na
    val naturalState: StateOfMatter,
    val appearance: String,
    val density: Double?,
    val meltingPoint: Double?,          // Kelvin
    val boilingPoint: Double?,          // Kelvin
    val electronegativity: Double?,
    val discoveredBy: String?,
    val discoveryYear: Int?,
    val trivia: List<String>,           // Fun facts
    val realWorldUses: List<String>,
    val whereFound: List<String>
)
```

**Grid Position Extension:**
```kotlin
fun Element.gridPosition(): GridPosition
```

---

#### ElementCategory.kt
**Enum Values:**
```kotlin
enum class ElementCategory(val displayName: String) {
    ALKALI_METAL("Alkali Metal"),
    ALKALINE_EARTH_METAL("Alkaline Earth Metal"),
    TRANSITION_METAL("Transition Metal"),
    POST_TRANSITION_METAL("Post-Transition Metal"),
    METALLOID("Metalloid"),
    NONMETAL("Reactive Nonmetal"),
    HALOGEN("Halogen"),
    NOBLE_GAS("Noble Gas"),
    LANTHANIDE("Lanthanide"),
    ACTINIDE("Actinide"),
    UNKNOWN("Unknown")
}
```

---

#### ElementsRepository.kt
**Purpose:** Static data source for all 118 elements

```kotlin
object ElementsRepository {
    val elements: List<Element> = listOf(
        // All 118 elements with complete data
    )
}
```

---

## Interactive Features

### Category Filtering
- Click legend item → highlights elements of that category
- Other elements dim to 25% opacity
- Click same category again → clears filter

### Orientation Toggle
- Icon button in top-right corner (Refresh icon)
- Toggles between landscape (no scroll) and portrait (horizontal scroll)
- Actually changes screen orientation via ActivityInfo

### Element Detail Navigation
- Click any element → full-screen detail view
- Back arrow returns to grid

---

## Common Patterns

### Compose Modifiers
```kotlin
// Conditional modifier based on orientation
val cellModifier = if (isLandscape) Modifier.weight(1f) else Modifier.width(48.dp)

// Dimming effect for filtered elements
val alpha = if (isDimmed) 0.25f else 1f
Modifier.graphicsLayer { this.alpha = alpha }
```

### Color Usage
```kotlin
// Get category color
val color = element.category.toColor()

// Light background variant
color.copy(alpha = 0.15f)
```

---

## Recent Changes Log

### 2026-01-09: UI Cleanup + Enhanced Detail Panel (v10)
**Files Modified:**
- `Element.kt` - Fixed `gridPosition()` to return (-1,-1) for elements > 118 (prevents Uue overwriting Hydrogen)
- `PeriodicTableGrid.kt` - Filters out elements with invalid grid positions
- `ElementCard.kt` - Switched to SansSerif font for better fit: atomic number 8sp, symbol 18sp, name 8sp
- `PeriodicTableScreen.kt` - Removed TopBar with Element Relationships section
- `ElementDetailPanel.kt` - White background, added scrollable sections with comprehensive data

**UI Changes:**
- Removed "Element Relationships" dropdown and instructions from top bar
- Element card uses SansSerif font (more compact than Monospace) for better element name visibility
- Element detail panel background changed from sage green to white
- Atomic Profile section now includes:
  - Protons, Neutrons, Electrons, Valence Electrons
  - Valencies (from oxidation states)
  - Electron Configuration
  - Electronegativity, Electron Affinity
- Discovery section now includes:
  - Discovered By, Named By, Discovery Year, Name Origin
- Isotopes section redesigned with consistent PropertyRow layout:
  - Total, Stable, Radioactive counts
  - Stable isotopes with abundance percentages
  - Radioactive isotopes with half-life values
- Added new sections to element detail panel:
  - Atomic Radii (empirical, covalent, van der Waals)
  - Chemical Properties (oxidation states, electrode potential, crystal structure)
  - Thermal Properties (conductivity, fusion, vaporization, heat capacity)
  - Real World Uses (bullet list)
  - Where Found (bullet list)
  - Fun Facts (trivia with star bullets)
  - Abundance (universe, crust, ocean, human body)

**Bug Fixes:**
- Element 119 (Uue) no longer overwrites Hydrogen at position (0,0)
- Font sizes adjusted to prevent truncation in element cells

### 2026-01-08: JSON Data Migration + Element Images (v9)
**Files Created:**
- `ElementJsonParser.kt` - JSON parsing using kotlinx.serialization
- `assets/elements.json` - All 118 elements in JSON format (267KB)

**Files Modified:**
- `libs.versions.toml` - Added Coil 2.5.0 and kotlinx-serialization 1.6.2
- `build.gradle.kts` - Added coil-compose and kotlinx-serialization-json dependencies
- `Element.kt` - Added `imageUrl: String?` field, `@Serializable` annotations
- `ElementSections.kt` - Added `@Serializable` annotations to all data classes/enums
- `ElementsRepository.kt` - Simplified from ~15,000 lines to ~90 lines (loads from JSON)
- `ElementDetailPanel.kt` - Uses Coil AsyncImage for element photos
- `PeriodicTableScreen.kt` - Loads elements via `ElementsRepository.loadElements(context)`

**Architecture Changes:**
- Element data now stored in `assets/elements.json` instead of hardcoded Kotlin
- Data loaded at runtime via kotlinx.serialization
- Images from Wikimedia Commons loaded via Coil with element symbol fallback
- Repository requires Context to load assets (lazy initialization)

**New Dependencies:**
```toml
coil-compose = "2.5.0"
kotlinx-serialization-json = "1.6.2"
kotlin-serialization plugin
```

**JSON Structure:**
```json
{
  "version": 1,
  "source": "Bowserinator/Periodic-Table-JSON + komed3/periodic-table",
  "elements": [
    {
      "atomicNumber": 1,
      "symbol": "H",
      "name": "Hydrogen",
      "imageUrl": "https://upload.wikimedia.org/...",
      "sections": { ... }
    }
  ]
}
```

**Image Loading:**
- Uses `SubcomposeAsyncImage` from Coil
- Shows element symbol as fallback during loading or on error
- Images from Wikimedia Commons via komed3 data source

**Benefits:**
- Maintainability: Edit JSON without recompiling
- Future-proof: Can load from remote server for updates
- Smaller codebase: ~15,000 lines Kotlin → ~90 lines loader + 267KB JSON
- Real images: Actual element photos from Wikimedia Commons
- Offline-first: JSON bundled in assets, images cached by Coil

### 2026-01-08: Notion-Style UI + Enriched Element Data (v8)
**Files Modified:**
- `ElementsRepository.kt` - Regenerated with enriched data from komed3/periodic-table
- `ElementDetailPanel.kt` - Complete UI redesign with Notion-like visual hierarchy

**Data Enrichment:**
- 118 elements with comprehensive section data from multiple sources:
  - Bowserinator/Periodic-Table-JSON (base data)
  - komed3/periodic-table (physical/chemical properties)
  - komed3 nuclides.json (isotope data)
- Physical section now includes: atomic radius, thermal conductivity, heat capacity, Mohs hardness, magnetic type
- Chemical section now includes: oxidation states, standard electrode potentials
- Isotopes section now includes: stable isotopes with abundance, radioactive isotopes with half-life and decay modes

**UI Redesign:**
- Removed grade level badges (K-2, 3-5, etc.) - clean visual hierarchy instead
- Removed emoji icons from section headers and content
- Removed bottom page indicators (swipe-only navigation for sections)
- Added `SectionNavigationStrip` - shows current section name in center with prev/next section names dimmed on sides
- Sections use simple vertical layout with `SectionTitle` headers (colored, small caps)
- `PropertyItem` for key-value pairs, `BulletItem` for lists
- `NumberDisplay` for prominent numeric values
- `HorizontalDivider` for visual separation in Physical, Chemical, Isotopes sections

**New Layout Structure:**
```
ElementHeader (element card + name + close button)
SectionNavigationStrip (prev | Current | next)
HorizontalPager (6 sections)
ElementNavigation (< | >) - minimal element arrows only
```

**Section Content Style:**
- Hero visualization at top (cube, Bohr model, or numeric summary)
- Content flows with typography hierarchy (no container boxes)
- Section titles: 13sp, SemiBold, category color
- Values: 14-18sp, White or category color for emphasis
- Descriptions: 14sp, muted gray (#B8B8C8)

### 2026-01-08: Progressive Depth 6-Section Detail Panel (v7)
**Files Created:**
- `ElementSections.kt` - Comprehensive data models for all 6 progressive depth sections

**Files Modified:**
- `Element.kt` - Added `sections: ElementSections?` field + computed properties (protons, electrons, neutrons, valenceElectrons)
- `ElementDetailPanel.kt` - Complete rewrite with 6 swipeable sections

**New Architecture - Progressive Depth Sections:**
Each section has:
- Section header with icon and title
- Vertical scroll with grade-level content cards (K-2 → College+)
- Hero visualization at top

**6 Sections:**
1. **Overview** 🔍 - Simple description, quick facts, where found, fun fact, discovery, detailed description
2. **Atom** ⚛️ - Bohr model hero, particle counts, particle descriptions, periodic table position, atomic size
3. **Electrons** ⚡ - Shell diagram hero, electron shells (KLM), valence electrons, array notation, electron config, noble gas notation, ionization energy
4. **Physical** 🧊 - State visualization hero, appearance, density, temperature facts, hardness, magnetic properties
5. **Chemical** 🧪 - Reactivity indicator hero, reactivity level, common reactions, oxidation states, electronegativity, important compounds
6. **Isotopes** ☢️ - Isotope summary hero, isotope explanation, stable isotopes, radioactive isotopes, applications

**Data Models (ElementSections.kt):**
- `OverviewSection` - K-2 through 9-12 content levels
- `AtomSection` - Particle descriptions, family info, atomic radius
- `ElectronsSection` - Shell capacities, valence, configurations, ionization/affinity
- `PhysicalSection` - Appearance, thermal/electrical/magnetic properties
- `ChemicalSection` - Reactivity, reactions, oxidation states, compounds
- `IsotopesSection` - Stable/radioactive isotopes, applications

**Supporting Types:**
- `MagneticType` enum (Diamagnetic, Paramagnetic, Ferromagnetic, etc.)
- `ReactivityLevel` enum (Very Low → Very High)
- `ChemicalReaction`, `OxidationCompound`, `ElectrodePotential`, `ChemicalCompound`
- `DecayMode` enum (Alpha, Beta, EC, etc.)
- `Isotope`, `RadioactiveIsotope`, `StableIsotopeSimple`, `IsotopeApplication`

**Grade Level Colors:**
- K-2: Green (#4CAF50)
- 3-5: Yellow (#FFEB3B)
- 6-8: Orange (#FF9800)
- 9-12: Red (#F44336)
- 11-12: Purple (#9C27B0)
- College+: Deep Purple (#673AB7)

### 2026-01-08: Paged Detail Panel with Hero Visuals (v6)
**Files Modified:**
- `ElementDetailPanel.kt` - Complete rewrite with paged sections and hero visualizations

**New Architecture:**
- **Anchored Header**: Fixed at top showing element card (symbol, atomic number, mass) + element name
- **HorizontalPager**: 3 paged sections with snap scroll (not continuous scroll)
- **Section Navigation**: Swipe or use navigation buttons to move between sections

**Hero Visualizations:**
1. **Section 1 - Element Avatar**: 3D cube visualization with category-colored faces
2. **Section 2 - Bohr Model**: Hand-drawn style with:
   - Dashed/wobbly orbit lines for sketchy effect
   - Glowing electrons on shells
   - Glowing nucleus WITHOUT element symbol (just colored core)
3. **Section 3 - Emission Spectrum**: Colored spectral lines unique to each element (based on atomic number)

**Bottom Navigation:**
- Page indicators (3 dots showing current section)
- Section navigation (white arrows, inner)
- Element navigation (colored arrows - red prev, blue next, outer)

**Key Composables:**
- `ElementHeader(element, categoryColor, onClose)` - Anchored header
- `Section1BasicDetails(element, categoryColor)` - Avatar + trivia + quick facts
- `Section2ExploratoryDetails(element, categoryColor)` - Bohr model + electron config
- `Section3InDepthDetails(element, categoryColor)` - Spectrum + physical properties
- `ElementAvatarVisualization()` - 3D cube Canvas drawing
- `HandDrawnBohrModel()` - Sketchy Bohr model Canvas
- `EmissionSpectrumVisualization()` - Spectral lines Canvas
- `BottomNavigation()` - Page indicators + navigation

### 2026-01-08: Comprehensive Element Detail Panel (v5)
**Files Modified:**
- `ElementCard.kt` - Added borders to cells
- `PeriodicTableScreen.kt` - Overlay panel integration
- `ElementDetailPanel.kt` - NEW FILE - Comprehensive detail view

**New Architecture:**
- Element cards now have light gray borders (1dp, rounded 2dp)
- Detail panel is an **overlay** that slides in from right (doesn't resize table)
- Panel has close (X), previous (<), and next (>) navigation buttons
- Three-section detail view:
  1. **Basic Details**: Large symbol, name, atomic number, category badge, particles count, trivia
  2. **Exploratory Details**: Bohr model visualization, electron config, grid position, state points, uses
  3. **In-Depth Details**: Physical properties, where found, additional trivia
- Semi-transparent backdrop (30% black) behind panel, clickable to close

**Layout Structure:**
```
Box(fillMaxSize) {
    Column {
        PeriodicTableGrid(weight=1f)   // Always full size
        LegendBar(bottom)
    }
    AnimatedVisibility(backdrop)       // Fade in/out
    AnimatedVisibility(panel) {        // Slide in/out from right
        ElementDetailPanel(380dp wide)
    }
}
```

### 2026-01-08: Landscape-Only with Sidebar Layout (v4)
**Files Modified:**
- `PeriodicTableScreen.kt` - Complete rewrite with sidebar layout
- `PeriodicTableGrid.kt` - Simplified to landscape-only

**New Architecture:**
- Screen locked to **landscape mode only** (no toggle)
- Two-panel layout:
  - Left: Full periodic table (65% width when element selected)
  - Right: Element detail sidebar (35% width, appears on element click)
- Bottom: Horizontal scrolling legend bar
- Grid uses simple Row/Column with weight, white background
- No more orientation switching = no more frame drops

**Layout Structure:**
```
Column(fillMaxSize) {
    Row(weight=1f) {
        PeriodicTableGrid(weight=0.65f or 1f)   // Shrinks when sidebar open
        ElementDetailSidebar?(weight=0.35f)     // Shows on element click
    }
    LegendBar(bottom, horizontal scroll)
}
```

### 2026-01-08: Legend Clarity & Portrait Mode Fix (v3)
**Files Modified:**
- `PeriodicTableScreen.kt` - Clearer legend, robust orientation handling
- `PeriodicTableGrid.kt` - Performance optimizations with keys
- `ElementCard.kt` - Added minimum size constraints

**Changes:**
- Legend: Increased text to 13sp Medium, larger dots (12dp), 48dp height
- Legend items: More padding (12px horizontal, 8px vertical)
- Element cards: Added `defaultMinSize(44dp, 52dp)` for consistent sizing
- Portrait card width increased to 56dp for better visibility
- Added `key` composables for better recomposition performance
- Orientation: Now detects actual device orientation with user override
- Uses `SENSOR_LANDSCAPE/PORTRAIT` for smoother transitions
- Stable callback references to reduce recomposition

### 2026-01-08: Layout & Font Improvements (v2)
**Files Modified:**
- `ElementCard.kt` - Larger, bolder fonts (10sp/26sp Bold/10sp Medium)
- `PeriodicTableScreen.kt` - Moved orientation toggle to bottom-left above legend
- `PeriodicTableGrid.kt` - Fixed horizontal scroll for portrait mode

**Changes:**
- Orientation toggle now in bottom-left corner above legend
- Legend height reduced to 52dp with proper spacing
- Portrait mode horizontal scroll applied at Box level for proper scrolling
- Element symbol increased to 26sp Bold
- Element name increased to 10sp Medium for better readability

### 2026-01-08: UI Enhancement (v1)
**Files Modified:**
- `ElementCard.kt` - Increased font sizes (9sp/22sp/8sp)
- `PeriodicTableScreen.kt` - Added orientation toggle, fixed 56dp legend height
- `PeriodicTableGrid.kt` - Added isLandscape parameter, conditional scroll
- `ElementDetailScreen.kt` - Complete rewrite with sectioned layout

**Changes:**
- Element fonts now larger and readable
- Portrait mode has horizontal scroll, landscape fills screen
- Legend no longer overlaps bottom elements
- Detail screen shows ALL trivia and organized sections
