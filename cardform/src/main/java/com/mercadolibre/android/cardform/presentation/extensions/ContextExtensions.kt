package com.mercadolibre.android.cardform.presentation.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.TypedValue

private const val PLATFORM_MP = "MP"
private const val PLATFORM_ML = "ML"

internal fun Context.getPxFromDp(dp: Float): Float {
    return dp * resources.displayMetrics.density
}

internal fun Context.getPxFromSp(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
}

internal fun Context?.hasConnection(): Boolean {
    if (this != null) {
        try {
            var haveConnectedWifi = false
            var haveConnectedMobile = false
            val cm = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw = cm.activeNetwork ?: return false
                val actNw = cm.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            } else {
                val networkInfo = cm.activeNetworkInfo
                if (networkInfo != null && networkInfo.isConnected) {
                    if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                        if (networkInfo.isConnectedOrConnecting) {
                            haveConnectedWifi = true
                        }
                    }
                    if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                        if (networkInfo.isConnectedOrConnecting) {
                            haveConnectedMobile = true
                        }
                    }
                }
                return haveConnectedWifi || haveConnectedMobile
            }
        } catch (ex: Exception) {
            return false
        }
    } else {
        return false
    }
}

internal fun Context.isML() = getPlatform() == PLATFORM_ML
internal fun Context.isMP() = getPlatform() == PLATFORM_MP

internal fun Context.getPlatform(): String {
    if (applicationInfo.packageName.contains("com.mercadolibre")) {
        return PLATFORM_ML
    }

    return PLATFORM_MP
}