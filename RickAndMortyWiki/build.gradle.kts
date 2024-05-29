// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        maven {url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.23")
    }
}
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.23" apply false
}

