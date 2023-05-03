import com.google.protobuf.gradle.id

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.protobuf")
    id("com.mikepenz.aboutlibraries.plugin")
}

android {
    namespace = "org.bandev.buddhaquotescompose"

    compileSdk = 33

    defaultConfig {
        applicationId = "org.bandev.buddhaquotescompose"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.current()
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.04.01"))
    implementation("androidx.browser:browser:1.5.0")
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3:1.1.0-rc01")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.core:core-splashscreen:1.0.1")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // AboutLibraries - https://github.com/mikepenz/AboutLibraries
    val aboutLibrariesVersion = "10.6.2"
    implementation("com.mikepenz:aboutlibraries-core:$aboutLibrariesVersion")
    implementation("com.mikepenz:aboutlibraries-compose:$aboutLibrariesVersion")

    // Accompanist - https://github.com/google/accompanist
    val accompanistVersion = "0.31.1-alpha"
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

    // Compose Settings - https://github.com/alorma/Compose-Settings
    implementation("com.github.alorma:compose-settings-ui-m3:0.26.0")

    // Datastore
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("com.google.protobuf:protobuf-javalite:3.22.3")

    // Kotlin Coroutines - https://github.com/Kotlin/kotlinx.coroutines
    val coroutinesVersion = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Lifecycle - https://developer.android.com/jetpack/androidx/releases/lifecycle
    val lifecycleVersion = "2.6.1"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // Room - https://developer.android.com/training/data-storage/room
    val roomVersion = "2.5.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Sheets
    val sheetsVersion = "1.1.1"
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:$sheetsVersion")
    implementation("com.maxkeppeler.sheets-compose-dialogs:duration:$sheetsVersion")
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.22.3"
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
