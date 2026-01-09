# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Codebase Context File

**IMPORTANT:** Before exploring the codebase, read `CODEBASE_CONTEXT.md` first. This file contains:
- Detailed file structure and purpose of each file
- Key data models and their fields
- Current UI components and their parameters
- Color schemes, font sizes, and layout patterns
- Recent changes log

**Workflow:**
1. **Start of session:** Read `CODEBASE_CONTEXT.md` to understand current state
2. **During work:** Use the context file to navigate efficiently without re-reading files
3. **After changes:** Update `CODEBASE_CONTEXT.md` with any modifications made:
   - New files added
   - Changed parameters/functions
   - Updated UI patterns
   - Add entry to "Recent Changes Log" section

This reduces redundant file exploration and maintains accurate documentation.

## Architecture: Modular Content Platform

DiscoveryLab is a modular interactive content platform built with Kotlin and Jetpack Compose.

### Module Structure

```
:app                        # The Shell - host application, minimal logic, top-level navigation
:core:ui                    # The Foundation - shared design system (colors, typography, Compose primitives)
:content:periodic-table     # The Content - first interactive pack
:content:*                  # Additional interactive packs (self-contained modules)
build-logic/convention      # Convention plugins for shared build configuration
```

### Module Responsibilities

- **:app (The Shell)** - Host module that bootstraps the application and handles top-level navigation. Contains only `MainActivity`. All UI delegated to `:core:ui`.
- **:core:ui (The Foundation)** - Design system module containing `DiscoveryLabTheme`, color schemes, typography, and reusable Compose components. All UI modules depend on this.
- **:content:\* (The Content)** - Independent interactive modules. Each is self-contained and depends on `:core:ui` but not on other content modules.

### Dependency Graph

```
:app
 ├── :core:ui
 └── :content:*
       └── :core:ui
```

## Standards & Patterns

- **Styling**: All UI must use `DiscoveryLabTheme`. No hardcoded colors in content modules.
- **Theme Access**:
  - `DiscoveryLabTheme.colors` - Color palette with semantic tokens
  - `DiscoveryLabTheme.style` - Legacy typography (11 tokens)
  - `DiscoveryLabTheme.typography` - Extended typography (26 tokens)
- **State**: Prefer `Flow` and `ViewModel` for interactive logic.

## Convention Plugins

New content modules should use the `discovery.content` plugin:

```kotlin
plugins {
    id("discovery.content")
}

android {
    namespace = "com.example.discoverylab.content.modulename"
}

dependencies {
    implementation(project(":core:ui"))
}
```

## Build Commands

```bash
# Build debug APK (use Android Studio JDK if system Java is 25+)
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ./gradlew assembleDebug

# Build release APK
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ./gradlew assembleRelease

# Clean build
./gradlew clean

# Install on connected device/emulator
./gradlew installDebug
```

## Testing Commands

```bash
# Run all unit tests
./gradlew test

# Run tests for a specific module
./gradlew :core:ui:test
./gradlew :content:periodic-table:test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest
```

## Technical Stack

- **Kotlin**: 2.0.21
- **Compose BOM**: 2024.12.01 (Material 3)
- **AGP**: 8.7.2
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Gradle**: Version Catalog (`libs.versions.toml`) + Convention Plugins (`build-logic`)
