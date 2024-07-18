plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "org.cnodejs.android.md"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.cnodejs.android.md"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "2.0.0"

        buildConfigField("String", "HOST_BASE_URL", "\"https://cnodejs.org\"")
        buildConfigField("String", "USER_AGENT_NAME", "\"CNodeMD\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes.add("DebugProbesKt.bin")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.8.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
    implementation("androidx.datastore:datastore:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.squareup.moshi:moshi:1.15.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("io.coil-kt:coil-base:2.6.0")
    implementation("io.coil-kt:coil-gif:2.6.0")
    implementation("io.coil-kt:coil-svg:2.6.0")
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("org.jsoup:jsoup:1.15.2")
    implementation("org.commonmark:commonmark:0.19.0")
    implementation("org.commonmark:commonmark-ext-autolink:0.19.0")
    implementation("org.commonmark:commonmark-ext-gfm-strikethrough:0.19.0")
    implementation("org.commonmark:commonmark-ext-gfm-tables:0.19.0")
    implementation("org.commonmark:commonmark-ext-heading-anchor:0.19.0")
    implementation("org.commonmark:commonmark-ext-ins:0.19.0")
    implementation("org.commonmark:commonmark-ext-yaml-front-matter:0.19.0")
    implementation("org.commonmark:commonmark-ext-image-attributes:0.19.0")
    implementation("org.commonmark:commonmark-ext-task-list-items:0.19.0")
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:hfrecyclerview:0.0.3")
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:loadmorefooter:0.0.3")
    implementation("com.github.TakWolf.Android-InsetsWidget:insetswidget:0.0.1")
    implementation("com.github.TakWolf.Android-InsetsWidget:constraintlayout:0.0.1")
    implementation("com.github.hadilq:live-event:1.3.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")
}
