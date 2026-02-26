plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.croptrack"
    compileSdk = 35
// ABHISHEK
    buildFeatures{
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.croptrack"
        minSdk = 26
        targetSdk = 35
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
}

dependencies {
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.0")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.0")
    implementation("androidx.core:core-ktx:1.15.0") {
        exclude(group = "com.google.guava", module = "guava")
    }
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.airbnb.android:lottie:6.6.6")
    implementation("com.hbb20:ccp:2.7.3")
    implementation("com.github.Drjacky:ImagePicker:2.3.22")
    implementation("androidx.databinding:compiler:3.2.0-alpha11")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
//    abhishek
// Retrofit core
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
// Retrofit → Gson converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
}

configurations.all {
    resolutionStrategy {
        // Force Guava to be the latest version (32.0.1-jre)
        force("com.google.guava:guava:32.0.1-jre")

        // Exclude older Guava versions (like 23.0) if they are found
        eachDependency {
            if (requested.group == "com.google.guava" && requested.version == "23.0") {
                useVersion("32.0.1-jre")
            }
        }
    }
}
