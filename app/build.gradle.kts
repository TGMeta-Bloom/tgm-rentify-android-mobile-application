import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.androidx.navigation.safeargs)
    // Firebase Google Services Plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.tgmrentify"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tgmrentify"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Firebase BOM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))

    // For ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // For LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // For the 'by viewModels()' delegate in fragments
    implementation(libs.androidx.fragment.ktx)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.12.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}