plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.thefortnightly"
    compileSdk = ConfigVersions.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.thefortnightly"
        minSdk = ConfigVersions.minSdkVersion
        targetSdk = ConfigVersions.targetSdkVersion
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
        getByName("debug") {
            val apiKey: String = project.property("NEWS_API_KEY") as String
            buildConfigField("String", "NEWS_API_KEY", apiKey)
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/gradle/incremental.annotation.processors"
                //"/META-INF/{AL2.0,LGPL2.1}"
            )
        )
    }
}

dependencies {

    implementation(Libs.kotlin_std_lib)
    implementation(Libs.fragment)
    implementation(Libs.app_compat)
    implementation(Libs.cardview)
    implementation(Libs.recyclerview)
    //implementation(Libs.androidx_annotations)
    implementation(Libs.androidx_legacy)

    // Material design
    implementation(Libs.material_design)

    // Architectural components
    implementation(Libs.viewmodelKtx)
    implementation(Libs.livedataKtx)
    implementation(Libs.lifecycleRuntime)
    implementation(Libs.runtimeKtx)
    implementation(Libs.lifecycleCompiler)

    // Activity Ktx for viewModels()
    implementation(Libs.activityKtx)

    // Room
    implementation(Libs.roomRuntime)
    kapt(Libs.roomCompiler)

    // Kotlin extensions and coroutines support for room.
    implementation(Libs.roomKtx)

    // Coroutines
    implementation(Libs.coroutines)
    implementation(Libs.coroutinesCore)

    // Navigation components
    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUiKtx)

    // Dagger - Hilt
    implementation(Libs.hilt)
    implementation(Libs.hiltCompiler)
    kapt(Libs.androidxHiltCompiler)

    // Paging 3
    implementation(Libs.pagingRuntime)

    // Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.gson)

    // Glide
    implementation(Libs.glide)
    kapt(Libs.glideCompiler)

    // Dependencies for local unit tests
    testImplementation(Libs.junit)
    testImplementation(Libs.coreTesting)
    testImplementation(Libs.robolectric)
    testImplementation(Libs.coroutinesTest)

    // Dependencies for Android instrumented unit tests
    androidTestImplementation(Libs.junit)
    androidTestImplementation(Libs.extJunitTest)
    androidTestImplementation(Libs.coreTesting)
    androidTestImplementation(Libs.mockito)
    androidTestImplementation(Libs.coroutinesTest)
    androidTestImplementation(Libs.hiltTest)
    kapt(Libs.androidHiltCompiler)
    androidTestImplementation(Libs.espresso)
    androidTestImplementation(Libs.espressoContrib)
    androidTestImplementation(Libs.truth)
    androidTestImplementation(Libs.turbine)
    debugImplementation(Libs.fragmentTest)


    implementation(Libs.androidxTestCore)
    implementation(Libs.espressoIdlingResource)

    // AndroidX Test - JVM testing
    testImplementation(Libs.coreKtxTest)
    testImplementation(Libs.extJunitTest)
}

// Non-existent type correction. Needed for Hilt.
kapt {
    correctErrorTypes = true
}