plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "edu.upc.projecte"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.upc.projecte"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
        }

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets["main"].jniLibs.srcDirs("src/main/jniLibs")

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    //implementation("com.google.androidgamesdk:game-activity:1.3.0")
    //implementation("androidx.core:core-ktx:1.10.1")
    //implementation("libs:games-activity:3.0.5")




}