

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.ferreteriahogar"
    compileSdk = 36
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
    defaultConfig {
        applicationId = "com.example.ferreteriahogar"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ML Kit Barcode Scanning
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
    implementation(libs.androidx.constraintlayout.core)
    implementation(libs.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.foundation)
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-framework-engine:5.9.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
// CameraX para usar la cámara en Compose
    implementation("androidx.camera:camera-core:1.5.1")
    implementation("androidx.camera:camera-camera2:1.5.1")
    implementation("androidx.camera:camera-lifecycle:1.5.1")
    implementation("androidx.camera:camera-view:1.5.1")
    implementation("androidx.camera:camera-extensions:1.5.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
// Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("androidx.compose.foundation:foundation:1.6.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.androidx.compose.ui)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    implementation("androidx.compose.material:material-icons-extended")

    implementation("androidx.navigation:navigation-compose:2.9.5")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")
}