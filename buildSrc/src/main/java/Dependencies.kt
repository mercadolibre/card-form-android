object Dependencies {

    // Gradle
    const val gradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val gradleKotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.gradleKotlin}"

    // Deploy
    const val bintray = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"
    const val maven = "com.github.dcendents:android-maven-gradle-plugin:2.1"

    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.gradleKotlin}"
    const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    // AppCompat
    const val appCompat = "com.android.support:appcompat-v7:${Versions.support}"
    const val support = "com.android.support:support-v4:${Versions.support}"
    const val design = "com.android.support:design:${Versions.support}"

    // ConstraintLayout
    const val constraintLayout =
        "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"

    // Android Lifecycle
    const val extensions = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    const val livedataCore = "android.arch.lifecycle:livedata-core:${Versions.lifecycle}"
    const val viewmodel = "android.arch.lifecycle:viewmodel:${Versions.lifecycle}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitCoroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // Okhttp
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "com.android.support.test:runner:${Versions.runner}"


    // Mercado libre
    const val meliUI = "com.mercadolibre.android:ui:${Versions.meliUI}"
    const val carddrawer = "com.mercadolibre.android:carddrawer:${Versions.carddrawer}"
    const val picassoDiskCache = "com.mercadolibre.android:picasso-disk-cache:${Versions.picassoDiskCache}"
}