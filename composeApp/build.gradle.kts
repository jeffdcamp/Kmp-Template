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

    jvm("desktop")

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

            api(libs.koin.android)
            api(libs.koin.androidx.compose)
            implementation(libs.kotlin.coroutines)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.preview)
            implementation(compose.components.uiToolingPreview)

            // Code
            implementation(libs.kotlin.serialization.json)
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.datetime)
            implementation(libs.okio)
            implementation(libs.kermit)
            implementation(libs.dbtools.kmp.commons)

            // Inject
            implementation(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.core)
            api(libs.koin.compose)
//            api(libs.koin.annotations)

            // UI
            implementation(libs.compose.material3.adaptive)
            implementation(libs.compose.material3.adaptive.navigation)
            implementation(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.jetbrains.navigation.compose)

            implementation(compose.ui)
            implementation(compose.components.resources)
//            implementation(libs.compose.material.iconsext)

            // Database
            implementation(libs.room.runtime)
//            implementation(libs.room.ktx)
            implementation(libs.sqlite.bundled)
            implementation(libs.datastorePrefs)
//            ksp(libs.androidx.room.compiler)
//            implementation(libs.dbtools.room)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlin.coroutines.swing)

//            implementation(libs.compose.material3.adaptive)
//            implementation(libs.compose.material3.adaptive.navigation)
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
    androidResources {
        generateLocaleConfig = true
    }
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
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
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

// ./gradlew koverHtmlReport
// ./gradlew koverVerify
kover {
//    currentProject {
//        createVariant("release") {
////            addWithDependencies(/*dependent project*/)
//        }
//    }
    reports {
//        variant("release") {
//            // reports settings
//        }
        verify {
            rule {
                minBound(0)
            }
        }
    }

    reports {

    }

//    reports {
//        defaults {
//            // adds the contents of the reports of `release` Android build variant to default reports
//            mergeWith("release")
//        }
//
//        verify {
//            rule {
//                minBound(0)
//            }
//        }
//    }
}
