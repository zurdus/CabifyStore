plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.zurdus.cabifystore"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.zurdus.cabifystore"
        minSdk = 26
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:catalog"))
    implementation(project(":feature:checkout"))

    implementation(project(":domain:product"))
    implementation(project(":domain:checkout"))

    implementation(project(":data:product:impl"))
    implementation(project(":data:cart:impl"))
    implementation(project(":data:discount:impl"))

    implementation(project(":base:common"))
    implementation(project(":base:ui"))
    implementation(project(":base:util"))

    implementation(project(":navigation"))

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.activity:activity-compose:1.7.2")

    implementation(platform("androidx.compose:compose-bom:2023.04.01"))
    implementation("androidx.compose.material:material:1.5.0-beta02")

    implementation("androidx.navigation:navigation-runtime-ktx:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.6.0")

    implementation("io.insert-koin:koin-android:3.4.2")
}
