plugins {
    alias(libs.plugins.android.application)
    //Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.test.sniptcode"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.sniptcode"
        minSdk = 23
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")

    // Firebase
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-auth")
    implementation("androidx.credentials:credentials:1.0.0")
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

    implementation ("com.google.android.gms:play-services-maps:18.0.2")


}