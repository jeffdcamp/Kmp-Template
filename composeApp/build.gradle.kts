
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.versions) // ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
    alias(libs.plugins.kover)
    alias(libs.plugins.download)
    alias(libs.plugins.mokoResources)
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
        optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }

    androidLibrary {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "org.jdc.kmp.shared.template"
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

//    linuxX64()

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "ComposeApp"
//            isStatic = true
//        }
//    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            // Firebase
            implementation(libs.firebase.google.analytics)
            implementation(libs.firebase.google.config)

            implementation(libs.firebase.gitlive.analytics)
            implementation(libs.firebase.gitlive.config)
        }
        commonMain.dependencies {
            // Code
            implementation(libs.kotlin.serialization.json)
            implementation(libs.kotlin.atomicfu)
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.datetime)
            implementation(libs.okio)
            implementation(libs.kermit)
            implementation(libs.dbtools.kmp.commons)
            implementation(libs.dbtools.kmp.commons.compose)
            implementation(libs.dbtools.kmp.room)

            // Inject
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // UI
            implementation(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material.iconsext)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.material3.adaptive)
            implementation(libs.jetbrains.compose.material3.adaptive.navigation)
            implementation(libs.jetbrains.compose.ui.tooling.preview)
            implementation(libs.jetbrains.navigation3.ui)

            // Resources
            implementation(libs.moko.resources)
            implementation(libs.moko.resources.compose)

            // Database
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.datastorePrefs)

            // firebase
            implementation(libs.firebase.gitlive.analytics)
            implementation(libs.firebase.gitlive.config)
        }
        commonTest.dependencies {
            implementation(libs.koin.test)
            implementation(libs.room.testing)
            implementation(libs.konsist)
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.coroutines.test)
            implementation(libs.assertk)
            implementation(libs.mockK)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlin.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.jdc.kmp.template"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    // Room
    add("kspAndroid", libs.room.compiler)
    add("kspDesktop", libs.room.compiler)
//    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
//    add("kspIosX64", libs.androidx.room.compiler)
//    add("kspIosArm64", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

// ===== String and other Resources =====
// ./gradlew generateMRcommonMain
multiplatformResources {
    resourcesPackage.set("org.jdc.kmp.template") // required
    resourcesClassName.set("SharedResources") // optional, default MR
//    resourcesVisibility.set(MRVisibility.Public) // optional, default Public
    iosBaseLocalizationRegion.set("en") // optional, default "en"
//    iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
}

