import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import de.undercouch.gradle.tasks.download.Download


plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.mokoResources) apply false

    alias(libs.plugins.download)
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
    alias(libs.plugins.kotlin.android) apply false
}

// ===== Gradle Dependency Check =====
// ./gradlew dependencyUpdates -Drevision=release
// ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(
            version = candidate.version,
            includeStablePreRelease = false
        )
    }
}

fun isNonStable(version: String, includeStablePreRelease: Boolean): Boolean {
    val stablePreReleaseKeyword = listOf("RC", "BETA").any { version.uppercase().contains(it) }
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+$".toRegex()
    val isStable = if (includeStablePreRelease) {
        stableKeyword || regex.matches(version) || stablePreReleaseKeyword
    } else {
        stableKeyword || regex.matches(version)
    }
    return isStable.not()
}

allprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.download.get().pluginId)

    // ===== Detekt =====
    // download detekt config file
    tasks.register<Download>("downloadDetektConfig") {
        download {
            onlyIf { !file("$projectDir/build/config/detektConfig.yml").exists() }
            src("https://mobile-cdn.churchofjesuschrist.org/android/build/detekt/v2/detektConfig-latest.yml")
            dest("$projectDir/build/config/detektConfig.yml")
        }
    }

    // ./gradlew detekt
    // ./gradlew detektDebug (support type checking)
    detekt {
        source.setFrom("src/main/kotlin", "src/commonMain/kotlin", "src/desktopMain/kotlin", "src/androidMain/kotlin")
        allRules = true // fail build on any finding
        buildUponDefaultConfig = true // preconfigure defaults
        config.setFrom(files("$projectDir/build/config/detektConfig.yml")) // point to your custom config defining rules to run, overwriting default behavior
        //    baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
    }

    tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
        dependsOn("downloadDetektConfig")

        // ignore ImageVector files
        exclude("**/ui/compose/icons/**")

        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
        }
    }
}
