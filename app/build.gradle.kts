plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp") version "1.9.0-1.0.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

android {
    namespace = "com.example.english4d"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.english4d"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    //Jsoup
    implementation("org.jsoup:jsoup:1.14.3")
    implementation(kotlin("reflect"))
    //Icon
    implementation ("androidx.compose.material:material-icons-core:1.6.3")
    implementation ("androidx.compose.material:material-icons-extended:1.6.3")
    //NavController
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    //Gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.2.2")
    //serialization
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit with Kotlin serialization Converter
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    //Room
    implementation("androidx.room:room-runtime:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")

}
secrets {
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"

}
