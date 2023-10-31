import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
    id ("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.pinto.mymovieappkt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pinto.mymovieappkt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        kapt {
//            arguments {
//                arg("room.schemaLocation", "$projectDir/schemas")
//            }
//        }
        val tmdbKey: String = gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY") ?: ""
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbKey\"")
        val youtubeKey: String = gradleLocalProperties(rootDir).getProperty("YOUTUBE_API_KEY") ?: ""
        buildConfigField("String", "YOUTUBE_API_KEY", "\"$youtubeKey\"")
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

    dataBinding {
        enable = true
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    kapt {
        correctErrorTypes= true
    }


    kotlin {
        jvmToolchain(17)
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(files("libs/YouTubeAndroidPlayerApi.jar"))

    //Navigation
    val navVersion = "2.6.0"
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    //Lifecycle
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    //Hilt
    val hiltVersion = "2.46.1"
    implementation ("com.google.dagger:hilt-android:$hiltVersion")
    kapt ("com.google.dagger:hilt-compiler:$hiltVersion")

    //Room
    val roomVersion = "2.6.0"
    implementation ("androidx.room:room-ktx:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")

    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //Retrofit + Gson
    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.google.code.gson:gson:2.10.1")

    //Glide + Glide Transformations
    val glideVersion = "4.15.1"
    implementation ("com.github.bumptech.glide:glide:$glideVersion")
    kapt ("com.github.bumptech.glide:compiler:$glideVersion")
    implementation ("jp.wasabeef:glide-transformations:4.3.0")

    //Palette
    implementation ("androidx.palette:palette-ktx:1.0.0")

    //Custom Views
    implementation ("com.github.chrisbanes:PhotoView:2.3.0")
    implementation ("com.github.cachapa:ExpandableLayout:2.9.2")

    //LeakCanary
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.12")
}