package com.cancer666.ffoptimize.data

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import com.cancer666.ffoptimize.config.AppConfig
import com.cancer666.ffoptimize.model.DeviceSpec
import com.cancer666.ffoptimize.model.SocTier
import java.io.File

class DeviceRepository(private val context: Context) {

    fun read(): DeviceSpec {
        val ram = readRamGb()
        val cores = Runtime.getRuntime().availableProcessors()
        return DeviceSpec(
            model = Build.MODEL ?: "unknown",
            manufacturer = Build.MANUFACTURER ?: "unknown",
            androidVersion = Build.VERSION.SDK_INT,
            ramGb = ram,
            cores = cores,
            socTier = classifySoc(ram, cores),
            abi = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown"
        )
    }

    private fun readRamGb(): Int {
        val f = File(AppConfig.PROC_MEMINFO)
        if (f.exists()) {
            var found: Int? = null
            f.forEachLine { line ->
                if (found == null && line.startsWith("MemTotal:")) {
                    val kb = line.filter { it.isDigit() }.toLongOrNull() ?: 0L
                    found = (kb / 1024 / 1024).toInt().coerceAtLeast(1)
                }
            }
            found?.let { return it }
        }
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        return (mi.totalMem / (1024L * 1024L * 1024L)).toInt().coerceAtLeast(1)
    }

    private fun classifySoc(ramGb: Int, cores: Int): SocTier = when {
        ramGb >= 8 && cores >= 8 -> SocTier.FLAGSHIP
        ramGb >= 4 && cores >= 6 -> SocTier.MID
        else -> SocTier.ENTRY
    }
}
