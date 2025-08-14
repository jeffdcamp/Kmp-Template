
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
//    alias(libs.plugins.mokoResources)
//    alias(libs.plugins.koin)

    alias(libs.plugins.versions) // ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
    alias(libs.plugins.kover)
    alias(libs.plugins.download)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    compilerOptions {
        compilerOptions {
            optIn.add("kotlin.time.ExperimentalTime")
            optIn.add("kotlin.uuid.ExperimentalUuidApi")
            optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }
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
//            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.kotlin.coroutines)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
//            implementation(compose.preview)
            implementation(compose.components.uiToolingPreview)

            // Code
            implementation(libs.kotlin.serialization.json)
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
//            implementation(libs.koin.compose.viewmodel.navigation)
//            api(libs.koin.annotations)

            // UI
            implementation(libs.compose.material3.adaptive)
            implementation(libs.compose.material3.adaptive.navigation)
//            implementation(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.jetbrains.navigation.compose)

//            implementation(libs.compose.material.iconsext)

            // Resources
//            implementation(libs.moko.resources)
//            implementation(libs.moko.resources.compose)

            // Navigation
//            implementation(libs.androidx.navigation3.runtime)
//            implementation(libs.androidx.navigation3.ui)
//            implementation(libs.androidx.lifecycle.viewmodel.navigation3)

            // Database
            implementation(libs.room.runtime)
//            implementation(libs.room.ktx) // doesn't seem to be needed... and causes issues with Desktop (adds kotlinx-coroutines-android)
            implementation(libs.sqlite.bundled)
            implementation(libs.datastorePrefs)
//            ksp(libs.androidx.room.compiler)
//            implementation(libs.dbtools.room)

            // firebase
//            implementation(libs.firebase.gitlive.auth)
            implementation(libs.firebase.gitlive.analytics)
//            implementation(libs.firebase.gitlive.config)
//            implementation(libs.firebase.gitlive.crashlytics)
//            implementation(libs.firebase.gitlive.firestore)
//            implementation(libs.firebase.gitlive.functions)
        }
        commonTest.dependencies {
            implementation(libs.room.testing)
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.coroutines.test)
            implementation(libs.assertk)
            implementation(libs.mockK)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
//            implementation(libs.kotlin.coroutines.swing)

//            implementation(libs.compose.material3.adaptive)
//            implementation(libs.compose.material3.adaptive.navigation)

            // Work-around for issue that somehow kotlinx-coroutines-android is being included with desktop dependencies (room-ktx?)
            // https://youtrack.jetbrains.com/issue/CMP-767/Module-with-the-Main-dispatcher-had-failed-to-initialize
//            configurations.commonMainApi {
//                exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
//            }
        }
    }
}

android {
    namespace = "org.jdc.kmp.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.jdc.kmptemplate"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
//    androidResources {
//        generateLocaleConfig = true
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        coreLibraryDesugaring(libs.android.desugar)
        debugImplementation(compose.uiTooling)
//        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.jdc.kmptemplate"
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
//multiplatformResources {
//    resourcesPackage.set("org.jdc.kmp.template") // required
//    resourcesClassName.set("Resources2") // optional, default MR
////    resourcesVisibility.set(MRVisibility.Public) // optional, default Public
////    iosBaseLocalizationRegion.set("en") // optional, default "en"
////    iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
//}

// ./gradlew koverHtmlReport
// ./gradlew koverVerify
//kover {
//    reports {
//        verify {
//            rule {
//                minBound(0)
//            }
//        }
//    }
//
//    reports {
//
//    }
//}
