
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

// ./gradlew createLicenseReports
// ./gradlew --stacktrace -i createLicenseReports
licenseManager {
    variantName = "release"
    outputDirs = listOf("./src/main/assets", "./build/licenses")
    excludeGroups = listOf(
        // Church created libraries
        "org.lds.mobile", "org.lds.sqlite",

        // Commercial Licenses
        "com.adobe.marketing.mobile"
    )
    // https://office365lds.sharepoint.com/sites/CorrelationDepartment/SitePages/Open-Source-Software-Licenses-Master-Allow-List-and-Block-List.aspx

    // invalidLicenses = listOf("GPL","GNU","NonCommercial","NoDerivatives","ShareAlike","CPAL","EPL","MPL","RPL","SPL","WTFPL","Beerware","IPA","JSON","APSL","Artistic") +
    //        listOf("BCL", "CECILL", "CDDL", "CPL", "DSL", "EUPL", "GCC", "IPL", "Rs-RL", "ODbL", "OSL", "NPL", "OCLC", "NGPL", "Peer", "QPL", "Qmail", "RPSL", "Ruby", "Sleepycat", "SISSL", "Watcom")

    invalidLicensesUrl = "https://mobile-cdn.churchofjesuschrist.org/android/build/license/invalid-licenses.json"
    customLicenses = listOf(
        "SeeScore MusicXML rendering is used under license from Dolphin Computing www.seescore.co.uk"
    )
}

// ===== `License Report` =====
// ./gradlew generateLicenseReport
licenseReport {
    // By default this plugin will collect the union of all licenses from
    // the immediate pom and the parent poms. If your legal team thinks this
    // is too liberal, you can restrict collected licenses to only include the
    // those found in the immediate pom file
    // Defaults to: true
    unionParentPomLicenses = false

    configurations = arrayOf("releaseRuntimeClasspath")

    filters = arrayOf<com.github.jk1.license.filter.DependencyFilter>(
        com.github.jk1.license.filter.LicenseBundleNormalizer(),
//        com.github.jk1.license.filter.ExcludeTransitiveDependenciesFilter(),
    )

    renderers = arrayOf<com.github.jk1.license.render.ReportRenderer>(
        com.github.jk1.license.render.InventoryHtmlReportRenderer("licenses.html"),
        com.github.jk1.license.render.InventoryMarkdownReportRenderer(),
        com.github.jk1.license.render.JsonReportRenderer("licenses.json"),
        com.github.jk1.license.render.TextReportRenderer("licenses.txt"),
    )

    excludeGroups = arrayOf(
        // internal
        "org.ics.mobile",
        "org.lds.mobile",
        "org.lds.sqlite",

        // licensed
        "net.zetetic", // transitive of Okta
        "com.adobe.marketing", // Adobe
    )
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

