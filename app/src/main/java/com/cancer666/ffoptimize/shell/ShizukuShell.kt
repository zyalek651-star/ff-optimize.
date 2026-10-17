package com.cancer666.ffoptimize.shell

import android.content.Context
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rikka.shizuku.Shizuku

class ShizukuShell(private val context: Context) : ShellProvider {

    override fun label(): String = "Shizuku"

    override suspend fun exec(cmd: String): ShellResult = withContext(Dispatchers.IO) {
        val process = ProcessBuilder("sh", "-c", cmd).redirectErrorStream(true).start()
        val out = process.inputStream.bufferedReader().use { it.readText() }
        val code = process.waitFor()
        ShellResult(code == 0, out.trim())
    }

    companion object {
        fun isAvailable(): Boolean = try {
            Shizuku.pingBinder() &&
                Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        } catch (_: Throwable) {
            false
        }
    }
}
