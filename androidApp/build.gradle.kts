plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
}

kotlin {
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
        optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
        optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
        freeCompilerArgs.addAll(
            "-Xexplicit-backing-fields", // Added with Kotlin 2.3
            "-Xreturn-value-checker=check" // Added with Kotlin 2.3 (https://kotlinlang.org/docs/unused-return-value-checker.html#mark-functions-to-check-ignored-results)
        )
    }
}

android {
    namespace = "org.jdc.kmp.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        val debug by getting {
            versionNameSuffix = " DEV"
            applicationIdSuffix = ".dev"
            buildConfigField("long", "BUILD_TIME", "0l") // to improve build times, do allow change on every build

            // Enable signing to test Firebase
            // signingConfig = signingConfigs.getByName("upload")
        }
        val release by getting {
            versionNameSuffix = ""
            buildConfigField("long", "BUILD_TIME", "${System.currentTimeMillis()}l")
//            signingConfig = signingConfigs.getByName("upload")

            // R8
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.composeApp)

    // inject
    implementation(libs.koin.android)
//    implementation(libs.kotlin.coroutines)

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // Firebase
    implementation(libs.firebase.google.analytics)
    implementation(libs.firebase.google.config)
    implementation(libs.firebase.gitlive.analytics)
    implementation(libs.firebase.gitlive.config)

    coreLibraryDesugaring(libs.android.desugar)

    // Logging
    implementation(libs.kermit)

    // Test
    testImplementation(libs.kotlin.test)
}
