# Repository Guidelines

## Project Structure & Module Organization
- `composeApp/` is the shared Kotlin Multiplatform module. Shared code lives in `composeApp/src/commonMain`, shared tests in `composeApp/src/commonTest`, and platform source sets (for example `androidMain` or `desktopMain`) sit alongside. Room schema snapshots are stored in `composeApp/schemas/`.
- `androidApp/` is the Android entry app module. JVM unit tests are in `androidApp/src/test`, and instrumentation tests are in `androidApp/src/androidTest`.
- `iosApp/` contains the Xcode project (`iosApp/iosApp.xcodeproj`) used to build/run iOS.
- `build/` is generated output, including reports (detekt, dependency updates). Do not edit generated files.

## Build, Test, and Development Commands
Use the Gradle wrapper from the repo root:
- `./gradlew build` — assemble and run the standard test suite.
- `./gradlew desktopRun` — run the desktop target locally.
- `./gradlew test` — run unit tests for all variants.
- `./gradlew allTests` — run tests for all targets and produce the aggregated report.
- `./gradlew connectedDebugAndroidTest` — run Android instrumentation tests on a connected device.
- `./gradlew detekt` — run Kotlin static analysis (config is downloaded to `build/config/detektConfig.yml`).
- `./gradlew lint` — run Android lint.
- `./gradlew koverHtmlReport` — generate an HTML coverage report.
- `./gradlew dependencyUpdates -Drevision=release` — check dependency updates.

For iOS, open `iosApp/iosApp.xcodeproj` in Xcode; the build invokes `:composeApp:embedAndSignAppleFrameworkForXcode` automatically.

## Coding Style & Naming Conventions
- Kotlin uses 4-space indentation. Follow existing formatting and Compose/Kotlin idioms.
- Names follow Kotlin conventions: classes in `PascalCase`, functions/vars in `camelCase`, constants in `UPPER_SNAKE_CASE`.
- Keep package names under `org.jdc.kmp.template`.
- Run `detekt` before submitting changes that touch Kotlin code.

## Testing Guidelines
- Multiplatform tests use `kotlin.test` with AssertK/MockK/Koin test support in `composeApp/src/commonTest`.
- Android unit tests live in `androidApp/src/test`; instrumentation tests in `androidApp/src/androidTest` (AndroidX test).
- Name test files `*Test.kt` and keep DB migration tests alongside the migration code in `composeApp/src/commonTest/.../migration`.
- Coverage reporting is available via Kover; no minimum threshold is enforced by default.

## Commit & Pull Request Guidelines
- Commit messages are short, descriptive, and often sentence case (for example, “Updated versions / Code cleanup”). If a commit covers multiple changes, separate them with ` / `.
- PRs should include a brief summary, list of tests run (or “not run” with reason), and screenshots or recordings for UI changes.
- Link related issues or tickets when applicable.

## Configuration & Local Overrides
- `local.properties` is used for machine-specific Android SDK settings; avoid committing local paths or secrets.
