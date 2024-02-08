plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    // For Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    // Navigation using: https://github.com/raamcosta/compose-destinations
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

android {
    namespace = "cz.mendelu.pef.pokeprojekt"
    compileSdk = 34

    defaultConfig {
        applicationId = "cz.mendelu.pef.pokeprojekt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "cz.mendelu.pef.pokeprojekt.HiltTestRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("com.google.android.material:material:1.5.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test:runner:1.5.2")
    implementation("androidx.test:rules:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.compose.runtime:runtime-livedata:[1.5.4]")
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.0-alpha04")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")


    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")



    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")


    // Moshi for json converter
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")


    // Navigation
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.8.42-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.8.42-beta")

    // DataStore
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Map
    implementation("com.google.maps.android:maps-compose:3.1.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha07")
    implementation("com.google.maps.android:android-maps-utils:3.5.3")
    implementation("com.google.maps.android:maps-compose-widgets:3.1.1")
    implementation("com.google.maps.android:maps-compose-utils:3.1.1")

    // Room
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.room:room-ktx:2.4.3")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("androidx.room:room-compiler:2.4.3")
    implementation("androidx.room:room-runtime:2.4.3")
    // End room

    // QR code
    implementation ("com.google.zxing:core:3.4.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")

    // Unit testing
    implementation ("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.9")

    implementation ("androidx.arch.core:core-testing:2.1.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")

    implementation ("com.google.dagger:hilt-android-testing:2.44")
    implementation ("androidx.test.uiautomator:uiautomator:2.2.0")



//MLKit
    // Layouts
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.annotation:annotation:1.2.0")
// CameraX
    implementation("androidx.camera:camera-camera2:1.0.0-SNAPSHOT")
    implementation("androidx.camera:camera-lifecycle:1.0.0-SNAPSHOT")
    implementation("androidx.camera:camera-view:1.0.0-SNAPSHOT")
// For graphic overlay
    implementation("com.google.guava:guava:27.1-android")
// text Recognition
    implementation("com.google.mlkit:text-recognition:16.0.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")


}

// For Hilt
kapt {
    correctErrorTypes = true
}
