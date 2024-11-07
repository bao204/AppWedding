plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.weddingpackage"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weddingpackage"
        minSdk = 24
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.google.android.material:material:1.4.0")

    implementation ("com.firebaseui:firebase-ui-database:8.0.2")

    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.orhanobut:dialogplus:1.11@aar")

    implementation ("com.google.code.gson:gson:2.8.8")

    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")

    implementation("com.google.android.gms:play-services-auth:21.0.0")

    implementation("com.github.momo-wallet:mobile-sdk:1.0.7")

    implementation("com.google.zxing:core:3.4.1")
    implementation("com.journeyapps:zxing-android-embedded:4.2.0")
//
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:18.0.0")
}