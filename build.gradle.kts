plugins {
    // Application
    alias(libs.plugins.android.application) apply false
    // Kotlin
    alias(libs.plugins.kotlin.android) apply false
    // Compose compiler
    alias(libs.plugins.compose.compiler) apply false
    // Android
    alias(libs.plugins.android.library) apply false
    // Kotlin jvm
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    // Ksp
    alias(libs.plugins.ksp) apply false
}