// Top-level build file where you can add configuration options common to all sub-projects/modules.

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

buildscript {
    buildscript {
        repositories {
            google()
            mavenCentral()
            maven("https://plugins.gradle.org/m2/")
        }
        dependencies {
            classpath("com.android.tools.build:gradle:8.1.0-beta01")
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
            classpath("com.mikepenz.aboutlibraries.plugin:aboutlibraries-plugin:10.6.1")
            classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.1")
        }
    }
}