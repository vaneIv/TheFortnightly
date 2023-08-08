
object Versions {
    val activityKtxVersion = "1.7.2"
    val androidXVersion = "1.0.0"
    val androidXLegacySupport = "1.0.0"
    val androidXTestCoreVersion = "1.5.0"
    val androidXTestExtKotlinRunnerVersion = "1.1.5"
    val androidXTestRulesVersion = "1.2.0"
    val androidxHiltCompiler = "1.0.0"
    val androidXAnnotations = "1.6.0"
    val appCompatVersion = "1.6.1"
    val lifecycleVersion = "2.6.1"
    val testingVersion = "2.2.0"
    val coroutinesVersion = "1.7.1"
    val cardVersion = "1.0.0"
    val dexMakerVersion = "2.28.1"
    val espressoVersion = "3.5.1"
    val fragmentVersion = "1.5.7"
    val glideVersion = "4.14.2"
    val hamcrestVersion = "1.3"
    val hiltVersion = "2.44"
    val junitVersion = "4.13.2"
    val materialVersion = "1.9.0"
    val mockitoVersion = "4.11.0"
    val navigationVersion = "2.6.0"
    val pagingVersion = "3.2.0"
    val recyclerViewVersion = "1.3.0"
    val retrofitVersion = "2.9.0"
    val robolectricVersion = "4.10.2"
    val roomVersion = "2.5.1"
    val rulesVersion = "1.0.1"
    val swipeRefreshLayoutVersion = "1.1.0"
    val truthVersion = "1.1.3"
    val turbineVersion = "1.0.0"
    val kotlinVersion = "1.8.20"
}

object Libs {
    // Kotlin
    val kotlin_std_lib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"

    // App dependencies
    val app_compat =  "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    val cardview = "androidx.cardview:cardview:${Versions.cardVersion}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    val androidx_annotations = "androidx.annotation:annotation:${Versions.androidXAnnotations}"
    val androidx_legacy = "androidx.legacy:legacy-support-v4:${Versions.androidXLegacySupport}"

    // Material Design
    val material_design = "com.google.android.material:material:${Versions.materialVersion}"

    // Architectural Components
    val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"

    // Lifecycle
    val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycleVersion}"
    val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"


    // Activity KTX for viewModels()
    val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtxVersion}"

    // Room
    val roomRuntime =  "androidx.room:room-runtime:${Versions.roomVersion}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    // Kotlin Extensions and Coroutines support for Room
    val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    // Coroutines
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$Versions.coroutinesVersion"


    // Navigation Components
    val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    //Dagger - Hilt
    val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    //implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    val androidxHiltCompiler = "androidx.hilt:hilt-compiler:${Versions.androidxHiltCompiler}"

    // Paging 3
    val pagingRuntime = "androidx.paging:paging-runtime:${Versions.pagingVersion}"

    // Retrofit
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"

    // Glide
    val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    // Dependencies for local unit tests
    val junit = "junit:junit:${Versions.junitVersion}"
    val coreTesting = "androidx.arch.core:core-testing:${Versions.testingVersion}"
    val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
    val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"

    // Dependencies for Android instrumented unit tests
    //androidTestImplementation "junit:junit:$junitVersion"
    val mockito = "org.mockito:mockito-core:${Versions.mockitoVersion}"
    //androidTestImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
    val hiltTest = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
    val androidHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    val truth = "com.google.truth:truth:${Versions.truthVersion}"
    val turbine = "app.cash.turbine:turbine:${Versions.turbineVersion}"

    // Testing code should not be included in the main code.
    // Once https://issuetracker.google.com/128612536 is fixed this can be fixed.
    val fragmentTest = "androidx.fragment:fragment-testing:${Versions.fragmentVersion}"
    val androidxTestCore =  "androidx.test:core:${Versions.androidXTestCoreVersion}"
    val espressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espressoVersion}"

    // AndroidX Test - JVM testing
    val coreKtxTest = "androidx.test:core-ktx:${Versions.androidXTestCoreVersion}"
    val extJunitTest = "androidx.test.ext:junit:${Versions.androidXTestExtKotlinRunnerVersion}"
    // testImplementation "app.cash.turbine:turbine:$turbineVersion"

    // AndroidX Test - Instrumented testing
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoVersion}"
}

object ConfigVersions {
    val compileSdkVersion = 33
    val minSdkVersion = 24
    val targetSdkVersion = 33
}