plugins {
    // Application
    alias(libs.plugins.android.application)
    // Kotlin
    alias(libs.plugins.kotlin.android)
    // Compose compiler
    alias(libs.plugins.compose.compiler)
    // Ksp
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.hitlogger"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.hitlogger"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            freeCompilerArgs.add("-Xcontext-parameters")
        }
    }
}

dependencies {

    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Core
    implementation(libs.androidx.core.ktx)
    // Activity
    implementation(libs.androidx.activity.compose)
    // Compose bom
    implementation(platform(libs.androidx.compose.bom))
    // Compose runtime
    implementation(libs.androidx.compose.runtime)
    // Compose preview
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    // Material 3
    implementation(libs.androidx.material3.android)
    //Calendar dialog
    implementation(libs.calendarPicker)
    // Jdk libs (for supporting new Api on old android versions)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}