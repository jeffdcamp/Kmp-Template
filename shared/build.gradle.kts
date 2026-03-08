
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
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

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "Shared"
//            isStatic = true
//        }
//    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            // Inject
            implementation(libs.koin.android)

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
            implementation(libs.dbtools.kmp.room)

            // Inject
            implementation(libs.koin.core)

            // Resources
            api(libs.moko.resources)

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
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.coroutines.test)
            implementation(libs.assertk)
            implementation(libs.mockK)
        }
        desktopMain.dependencies {
        }
    }
}

dependencies {
    // Room
    add("kspAndroid", libs.room.compiler)
    add("kspDesktop", libs.room.compiler)
//    add("kspIosSimulatorArm64", libs.room.compiler)
//    add("kspIosX64", libs.room.compiler)
//    add("kspIosArm64", libs.room.compiler)
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
