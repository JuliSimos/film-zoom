import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)

}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0

android {
    namespace = "ar.edu.unicen.filmzoom"
    compileSdk = 35

    defaultConfig {
        applicationId = "ar.edu.unicen.filmzoom"
        targetSdk = 35
        minSdk = 26
        versionCode = versionMajor * 100 + versionMinor * 10 + versionPatch
        versionName = "$versionMajor.$versionMinor.$versionPatch"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Leemos local.properties de forma segura desde la raíz del proyecto
        val localProperties = Properties()
        val localFile = rootProject.file("local.properties")
        if (localFile.exists()) {
            localFile.inputStream().use { stream ->
                localProperties.load(stream)
            }
        }
        val bearerToken = localProperties.getProperty("tmdb.bearer_token", "")
        buildConfigField("String", "TMDB_BEARER_TOKEN", "\"$bearerToken\"")
    }

    buildFeatures {
        compose = true
        buildConfig = true
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

}

dependencies {

    // Compose (gestionado por la BOM)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    // Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)


    // Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.compose.ui)

    // Hilt (Inyección de dependencias)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // HttpLoggingInterceptor
    implementation(libs.okhttp.logging)

    // Retrofit (Red)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)


    // Coil para Jetpack Compose
    implementation(libs.coil.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //Los compiladores o procesadores de anotaciones nunca van como implementation, sino como ksp
    ksp(libs.androidx.room.compiler)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    testImplementation(libs.kotlinx.coroutines.test)

    // Debugging / Previews
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
/*
Le dice a Gradle: "Si alguna librería de terceros intenta meter com.intellij:annotations (la versión 12.0 que choca), descartala automáticamente y quédate solo con la moderna de JetBrains".
 */
configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}