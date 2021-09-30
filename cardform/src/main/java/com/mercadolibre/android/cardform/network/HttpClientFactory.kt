package com.mercadolibre.android.cardform.network

import android.content.Context
import android.os.Build
import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.network.interceptor.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

internal object HttpClientFactory {

    private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB
    private const val TLS_1_2 = "TLSv1.2"
    private const val CACHE_DIR_NAME = "PX_OKHTTP_CACHE_SERVICES"
    private val LOGGING_INTERCEPTOR = if (BuildConfig.HTTP_CLIENT_LOG)
        HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    fun get(
        context: Context, connectTimeout: Int, readTimeout: Int,
        writeTimeout: Int,
        sessionId: String,
        flowId: String,
        accessToken: String?
    ): OkHttpClient {
        val cacheFile = getCacheDir(context)

        val client = OkHttpClient.Builder()
            .connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            .cache(Cache(cacheFile, CACHE_SIZE.toLong()))

        client.addInterceptor(LocaleInterceptor(context.applicationContext))
        client.addInterceptor(UserAgentInterceptor())
        client.addInterceptor(ScreenDensityInterceptor(context.applicationContext))
        client.addInterceptor(SessionInterceptor(sessionId))
        client.addInterceptor(FlowIdInterceptor(flowId))
        accessToken?.also { client.addInterceptor(AuthorizationInterceptor(it)) }

        // add logging interceptor (should be last interceptor)
        val loginInterceptor = HttpLoggingInterceptor()
        loginInterceptor.level = LOGGING_INTERCEPTOR
        client.addInterceptor(loginInterceptor)

        return enableTLS12(client).build()
    }

    private fun getCacheDir(context: Context): File {
        val cacheDir = context.cacheDir ?: context.getDir("cache", Context.MODE_PRIVATE)
        return File(cacheDir, CACHE_DIR_NAME)
    }

    private fun enableTLS12(clientBuilder: OkHttpClient.Builder): OkHttpClient.Builder {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            internalEnableTLS12(clientBuilder)
        } else {
            clientBuilder
        }
    }

    private fun internalEnableTLS12(client: OkHttpClient.Builder): OkHttpClient.Builder {
        val certificate = certificateTrustManager()
        return if (certificate != null) {
            configureProtocol(client, certificate)
        } else {
            client
        }
    }

    private fun configureProtocol(
        client: OkHttpClient.Builder,
        trustManager: X509TrustManager
    ): OkHttpClient.Builder {
        try {
            val sslContext = SSLContext.getInstance(TLS_1_2)
            sslContext.init(null, arrayOf<TrustManager>(trustManager), SecureRandom())
            client.sslSocketFactory(TLSSocketFactory(sslContext.socketFactory), trustManager)
            return client.connectionSpecs(availableConnectionSpecs())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return client
    }

    private fun availableConnectionSpecs(): List<ConnectionSpec> {
        val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
            )
            .tlsVersions(TlsVersion.TLS_1_2)
            .build()

        val connectionSpecsList = ArrayList<ConnectionSpec>()
        connectionSpecsList.add(connectionSpec)
        connectionSpecsList.add(ConnectionSpec.CLEARTEXT)
        return connectionSpecsList
    }

    private fun certificateTrustManager(): X509TrustManager? {
        try {
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers = trustManagerFactory.trustManagers
            return trustManagers[0] as X509TrustManager
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        } catch (e2: KeyStoreException) {
            e2.printStackTrace()
        }
        return null
    }
}