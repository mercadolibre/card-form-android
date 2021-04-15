object Dependencies {

    // Gradle
    const val gradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    // AppCompat
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"

    // ConstraintLayout
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    // Android Lifecycle
    const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val livedataCore = "androidx.lifecycle:lifecycle-livedata-core:${Versions.lifecycle}"
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // Okhttp
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "com.android.support.test:runner:${Versions.testRunner}"


    // Mercado libre
    const val ui = "com.mercadolibre.android:ui:${Versions.ui}"
    const val carddrawer = "com.mercadolibre.android:carddrawer:${Versions.carddrawer}"
    const val picassoDiskCache = "com.mercadolibre.android:picasso-disk-cache:${Versions.picassoDiskCache}"
    const val pxAddons = "com.mercadopago.android.px:addons:${Versions.pxAddons}"
    const val andesUi = "com.mercadolibre.android.andesui:components:${Versions.andesUi}"
}