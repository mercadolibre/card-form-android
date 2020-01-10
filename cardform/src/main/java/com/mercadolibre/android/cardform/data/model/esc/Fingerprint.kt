package com.mercadolibre.android.cardform.data.model.esc

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.text.TextUtils
import android.view.WindowManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.math.BigInteger
import java.security.SecureRandom
import java.util.*
import java.util.regex.Pattern

class Fingerprint(context: Context) {
    val vendorIds = getVendorIds(context)
    val model: String? = Build.MODEL
    val os = "android"
    val systemVersion = Build.VERSION.RELEASE
    val resolution = getDeviceResolution(context)
    val ram = getDeviceRam()
    val diskSpace = getDeviceDiskSpace()
    val freeDiskSpace = getDeviceFreeDiskSpace()
    val vendorSpecificAttributes = VendorSpecificAttributes(context)

    private fun getVendorIds(context: Context): List<VendorId> {
        val vendorIds = ArrayList<VendorId>()

        // android_id
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        vendorIds.add(VendorId("android_id", androidId))

        if (!TextUtils.isEmpty(Build.SERIAL) && "unknown" != Build.SERIAL) {
            vendorIds.add(VendorId("serial", Build.SERIAL))
        }

        // SecureRandom
        val randomId = SecureRandomId.getValue(context)
        if (!TextUtils.isEmpty(randomId)) {
            vendorIds.add(VendorId("fsuuid", randomId!!))
        }

        return vendorIds
    }

    private fun getDeviceRam(): Long? {
        var ram: Long? = null
        var reader: RandomAccessFile? = null
        try {
            reader = RandomAccessFile("/proc/meminfo", "r")
            val load = reader.readLine()

            val pattern = Pattern.compile("(\\d+)")
            val matcher = pattern.matcher(load)
            if (matcher.find()) {
                ram = java.lang.Long.valueOf(matcher.group(0))
            }
        } catch (ignored: Exception) {
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (ignored: Exception) {
                }

            }
        }

        return ram
    }

    private fun getDeviceDiskSpace(): Long {
        val statFs = StatFs(Environment.getDataDirectory().path)
        return statFs.blockSize.toLong() * statFs.blockCount.toLong() / 1048576
    }

    private fun getDeviceFreeDiskSpace(): Long {
        val statFs = StatFs(Environment.getDataDirectory().path)
        return statFs.blockSize.toLong() * statFs.availableBlocks / 1048576
    }

    private fun getDeviceResolution(context: Context): String {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay

        return display.width.toString() + "x" + display.height
    }

    private object SecureRandomId {
        private const val FILENAME_FSUUID = "fsuuid"

        private var fSUUID: String? = null

        @Throws(IOException::class)
        private fun readFile(file: File): String {
            val f = RandomAccessFile(file, "r")
            val bytes = ByteArray(f.length().toInt())
            f.readFully(bytes)
            f.close()
            return String(bytes)
        }

        @Throws(IOException::class)
        private fun writeFile(file: File) {
            val out = FileOutputStream(file)
            val random = SecureRandom()
            val id = BigInteger(64, random).toString(16)
            out.write(id.toByteArray())
            out.close()
        }

        @Synchronized
        fun getValue(context: Context): String? {
            if (fSUUID == null) {
                val path = Environment.getExternalStorageDirectory().absolutePath
                val file = File(path + "/" + context.packageName, FILENAME_FSUUID)
                try {
                    if (!file.exists()) {
                        val dirs = file.parentFile.mkdirs()
                        if (dirs) {
                            writeFile(file)
                        }
                    }
                    fSUUID = readFile(file)
                } catch (ignored: Exception) {
                }

            }

            return fSUUID
        }
    }

    data class VendorId(val name: String, val value: String)
}