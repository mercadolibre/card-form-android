package com.mercadolibre.android.cardform.data.model.esc

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class VendorSpecificAttributes(context: Context) {

    val featureCamera = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    val featureFlash = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    val featureFrontCamera = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
    val product = Build.PRODUCT
    val device = Build.DEVICE
    val platform = getSystemProperty(PLATFORM_PROPERTY)
    val brand = Build.BRAND
    val featureAccelerometer = context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)
    val featureBluetooth = context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)
    val featureCompass = context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)
    val featureGps = context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
    val featureGyroscope = context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE)
    val featureMicrophone = context.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
    val featureNfc = context.packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)
    val featureTelephony = context.packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)
    val featureTouchScreen = context.packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
    val manufacturer = Build.MANUFACTURER
    val screenDensity = context.resources.displayMetrics.density

    private fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            Log.e("Fingerprint", "Unable to read sysprop $propName", ex)
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    Log.e("Fingerprint", "Exception while closing InputStream", e)
                }

            }
        }
        return line
    }

    companion object {
        private const val PLATFORM_PROPERTY = "ro.product.cpu.abi"
    }
}