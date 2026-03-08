# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

All commands use the Gradle wrapper from the repo root:

```bash
./gradlew build                              # Assemble and run standard tests
./gradlew desktopRun                         # Run the desktop (JVM) target locally
./gradlew test                               # Run unit tests for all variants
./gradlew allTests                           # Run tests for all targets with aggregated report
./gradlew connectedDebugAndroidTest          # Run Android instrumentation tests
./gradlew detekt                             # Kotlin static analysis
./gradlew lint                               # Android lint
./gradlew koverHtmlReport                    # Generate HTML coverage report
./gradlew dependencyUpdates -Drevision=release  # Check for dependency updates
```

Run a single test class: `./gradlew desktopTest --tests "org.jdc.kmp.template.inject.AppKoinModuleCheck"`

iOS: open `iosApp/iosApp.xcodeproj` in Xcode (build invokes `:composeApp:embedAndSignAppleFrameworkForXcode` automatically).

## Project Structure

- **`shared/`** — Non-UI KMP module containing domain models, data layer (Room, DataStore), repositories, analytics, use cases, Koin DI modules, and moko-resources string localization. Consumable by both Compose and native iOS (SwiftUI) apps. Room schemas in `shared/schemas/`.
- **`composeApp/`** — Compose Multiplatform UI module. Contains screens, ViewModels, routes, navigation, theme, and Compose-specific Koin modules. Depends on `:shared`.
- **`androidApp/`** — Android app entry point. Depends on `:shared` and `:composeApp`. Unit tests in `src/test`, instrumentation tests in `src/androidTest`.
- **`iosApp/`** — Xcode project for iOS entry point (iOS target is currently commented out in build config).

Targets: Android (minSdk 26, targetSdk 36), Desktop (JVM 21), iOS (commented out but scaffolded).

## Architecture

MVVM with clean architecture layers split across two KMP modules:

### `shared` module (`org.jdc.kmp.template`):
```
analytics/       → Analytics abstraction (Firebase)
domain/          → Domain models, value classes, use cases
model/           → Repositories, DAOs, database entities, datastores
inject/          → Shared Koin DI modules (SharedKoinModules, DatabaseModule, AppCoroutineDispatchers)
```

### `composeApp` module (`org.jdc.kmp.template`):
```
ux/              → Screens (@Composable) + ViewModels + Routes
ui/              → Theme and shared UI components
inject/          → ViewModel Koin module (AppKoinModules)
```

### ViewModel Pattern

Each screen has a ViewModel extending `androidx.lifecycle.ViewModel` that exposes `StateFlow<UiState>`. UiState is a sealed interface with `Loading`, `Ready`, and `Empty` variants. Navigation is handled via `ViewModelNavigation3` delegate from dbtools-kmp-commons-compose.

### Navigation (Navigation3)

Type-safe routes as `@Serializable` data classes/objects implementing `NavKey`. Each route has a corresponding `RouteMatcher` for deep linking. Routes are wired to screens in `MainScreen.kt` via `entryProvider`. ViewModels navigate by calling `navigate(SomeRoute(...))`.

### Dependency Injection (Koin)

Shared modules registered in `shared/SharedKoinModules.kt` via `getSharedKoinModules()`. ViewModel modules in `composeApp/AppKoinModules.kt` via `getAllKoinModules()` (which includes shared modules). ViewModels use `viewModelOf(::ClassName)`, singletons use `singleOf(::ClassName)`. Route parameters passed to ViewModels via `parametersOf(key)`. Platform-specific modules (database, datastore, coroutines, filesystem) are in the shared module.

### Database (Room)

Room with SQLite bundled for KMP. Entities and DAOs in `shared/model/db/main/`. Schema version tracking in `shared/schemas/`. Migrations in `model/db/main/migration/` with tests in `shared/commonTest`.

### Domain Models

Inline value classes for type-safe IDs and fields (`IndividualId`, `HouseholdId`, `FirstName`, `Email`, etc.) with kotlinx-serialization serializers. Domain models in `shared/domain/` map to/from Room entities in repositories.

### Preferences

AndroidX DataStore with two sources: `DevicePreferenceDataSource` (device-specific) and `UserPreferenceDataSource` (user-specific). Located in the `shared` module.

## Key Libraries

- **Compose Multiplatform** (1.10.1) — Shared UI (composeApp only)
- **Room** (2.8.4) + SQLite bundled — Database (shared module)
- **Koin** (4.1.1) — Dependency injection
- **Ktor** (3.4.0) — HTTP client
- **kotlinx-serialization** — JSON serialization
- **moko-resources** — Multiplatform string localization (`SharedResources`, in shared module)
- **dbtools-kmp-commons / dbtools-kmp-commons-compose** — Navigation3 utilities, Flow extensions (`stateInDefault`)
- **Kermit** — Multiplatform logging
- **Detekt** — Static analysis (config downloaded at build time)
- **Kover** — Code coverage
- **Konsist** — Architecture verification in tests

## Testing

- Database and migration tests in `shared/src/commonTest`
- Koin module verification in `composeApp/src/commonTest` (`inject/AppKoinModuleCheck.kt`) — verifies all dependencies resolve, no duplicates, and all ViewModels are registered (uses Konsist)

Frameworks: `kotlin.test`, AssertK, MockK, Koin test.

## Conventions

- 4-space indentation, standard Kotlin naming (`PascalCase` classes, `camelCase` functions)
- Package: `org.jdc.kmp.template`
- Run `detekt` before submitting Kotlin changes
- Commit messages: short, sentence case, multiple changes separated with ` / `
- User-facing strings go through `SharedResources` (moko-resources in shared module), not hardcoded

## Adding a New Screen

1. Create a `@Serializable` route class implementing `NavKey` (with `RouteMatcher` for deep links)
2. Create a ViewModel with `StateFlow<UiState>` sealed interface
3. Create the `@Composable` screen function
4. Register the ViewModel in `composeApp/AppKoinModules.kt` with `viewModelOf(::ViewModel)`
5. Wire the route to the screen in `MainScreen.kt` via `entryProvider`

## Adding Shared Business Logic

1. Add domain models/use cases to `shared/domain/`
2. Add repository/data layer code to `shared/model/`
3. Register new singletons in `shared/SharedKoinModules.kt`
