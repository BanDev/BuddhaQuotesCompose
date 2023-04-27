plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.mikepenz.aboutlibraries.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "org.bandev.buddhaquotescompose"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true
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
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "org.bandev.buddhaquotescompose"
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.04.01"))
    implementation("androidx.browser:browser:1.5.0")
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // AboutLibraries - https://github.com/mikepenz/AboutLibraries
    implementation("com.mikepenz:aboutlibraries-core:8.9.4")

    // Accompanist - https://github.com/google/accompanist
    val accompanist_version = "0.23.1"
    implementation("com.google.accompanist:accompanist-insets:$accompanist_version")
    implementation("com.google.accompanist:accompanist-insets-ui:$accompanist_version")
    implementation("com.google.accompanist:accompanist-pager:$accompanist_version")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanist_version")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist_version")

    // Compose Icons - https://github.com/DevSrSouza/compose-icons
    implementation("br.com.devsrsouza.compose.icons.android:simple-icons:1.0.0")

    // Compose Markdown - https://github.com/jeziellago/compose-markdown
    implementation("com.github.jeziellago:compose-markdown:0.3.0")

    // Compose Settings - https://github.com/alorma/Compose-Settings
    implementation("com.github.alorma:compose-settings-ui:0.6.0")

    // Datastore
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("com.google.protobuf:protobuf-javalite:3.22.3")

    // Flinger - https://github.com/iamjosephmj/flinger
    implementation("com.github.iamjosephmj:Flinger:1.1.1")

    // Kotlin Coroutines - https://github.com/Kotlin/kotlinx.coroutines
    val coroutines_version = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    // Lifecycle - https://developer.android.com/jetpack/androidx/releases/lifecycle
    val lifecycle_version = "2.6.1"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // Room - https://developer.android.com/training/data-storage/room
    val room_version="2.4.3"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
}
