plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kover) apply false

    alias(libs.plugins.download)
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
}

allprojects {
    plugins.withId("org.jetbrains.compose") {
        extensions.configure<org.jetbrains.compose.ComposeExtension> {
            kotlinCompilerPlugin = libs.jetbrains.compose.compiler.get().toString()
        }
    }

    // Gradle Dependency Reports
    // ./gradlew -q app:dependencies --configuration debugCompileClasspath > deps.txt
    // ./gradlew app:dependencies --scan.

    // Gradle Dependency Check
    // ./gradlew dependencyUpdates -Drevision=release
    // ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
    apply(plugin = rootProject.libs.plugins.versions.get().pluginId)
    val excludeVersionContaining = listOf("alpha", "eap", "M1", "dev") // example: "alpha", "beta"
    // some artifacts may be OK to check for "alpha"... add these exceptions here
    val ignoreArtifacts = buildList {
        addAll(listOf("room-compiler"))

        // Compose
//        addAll(listOf("material3", "ui", "ui-tooling-preview", "ui-test-junit4", "ui-test-manifest", "material3-window-size-class", "compiler"))
//        addAll(listOf("window")) // material3 uses latest 1.1.0-alpha
    }

    tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
        resolutionStrategy {
            componentSelection {
                all {
                    if (ignoreArtifacts.contains(candidate.module).not()) {
                        val rejected = excludeVersionContaining.any { qualifier ->
                            candidate.version.matches(Regex("(?i).*[.-]$qualifier[.\\d-+]*"))
                        }
                        if (rejected) {
                            reject("Release candidate")
                        }
                    }
                }
            }
        }
    }

    // ===== Detekt =====
    // Known KMP issues https://github.com/detekt/detekt/issues/5611
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId).also {
        // download detekt config file
        tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadDetektConfig") {
            download {
                onlyIf { !file("$projectDir/build/config/detektConfig.yml").exists() }
                src("https://raw.githubusercontent.com/ICSEng/AndroidPublic/main/detekt/detektConfig-20231101.yml")
                dest("$projectDir/build/config/detektConfig.yml")
            }
        }

        // make sure when running detekt, the config file is downloaded
        tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
            // Target version of the generated JVM bytecode. It is used for type resolution.
            this.jvmTarget = "17"
            dependsOn("downloadDetektConfig")
        }

        detekt {
            allRules = true // fail build on any finding
            buildUponDefaultConfig = true // preconfigure defaults
            config.setFrom(files("$projectDir/build/config/detektConfig.yml")) // point to your custom config defining rules to run, overwriting default behavior
        }

        tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
            setSource(files(project.projectDir))
            exclude("**/build/**")
            exclude("**/*.kts")
            exclude("**/Platform.*.kt")


            exclude {
                it.file.relativeTo(projectDir).startsWith(buildDir.relativeTo(projectDir))
            }
        }

        tasks.register("detektAll") {
            dependsOn(tasks.withType<io.gitlab.arturbosch.detekt.Detekt>())
        }
    }

}
