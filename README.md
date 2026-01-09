# DiscoveryLab

An interactive periodic table Android app built with Kotlin and Jetpack Compose.

## Features

- **Interactive Periodic Table** - All 118 elements with accurate positioning
- **Element Details** - Comprehensive information for each element including:
  - Physical properties (atomic mass, density, melting/boiling points)
  - Atomic profile (protons, neutrons, electrons, valence electrons, electron configuration)
  - Discovery information (discovered by, named by, year, name origin)
  - Atomic radii, chemical properties, thermal properties
  - Real world uses and where found in nature
  - Fun facts and trivia
  - Abundance data (universe, earth's crust, ocean, human body)
  - Isotope information (stable and radioactive)
- **Category Filtering** - Filter elements by category (alkali metals, noble gases, etc.)
- **Color-Coded Categories** - Visual distinction between element types
- **Landscape Optimized** - Designed for tablet landscape mode

## Screenshots

*Coming soon*

## Architecture

DiscoveryLab follows a modular architecture:

```
:app                        # Host application, MainActivity
:core:ui                    # Shared design system (theme, colors, typography)
:content:periodic-table     # Periodic table feature module
```

### Tech Stack

- **Language**: Kotlin 2.0.21
- **UI**: Jetpack Compose with Material 3
- **Build**: Gradle with Convention Plugins
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)

## Building

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

Note: If using Java 25+, specify Android Studio's JDK:
```bash
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ./gradlew assembleDebug
```

## Project Structure

```
DiscoveryLab/
├── app/                           # Main application module
├── core/
│   └── ui/                        # Shared UI components and theme
├── content/
│   └── periodic-table/            # Periodic table feature
│       ├── data/                  # Element data models and repository
│       ├── ui/                    # UI components
│       └── assets/elements.json   # Element data (118 elements)
├── build-logic/                   # Convention plugins
└── gradle/                        # Version catalog
```

## Data Source

Element data sourced from:
- [Bowserinator/Periodic-Table-JSON](https://github.com/Bowserinator/Periodic-Table-JSON)
- [komed3/periodic-table](https://github.com/komed3/periodic-table)

## License

MIT License
