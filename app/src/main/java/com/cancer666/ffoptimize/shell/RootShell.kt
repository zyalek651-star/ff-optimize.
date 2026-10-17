package com.cancer666.ffoptimize.shell

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RootShell : ShellProvider {

    override fun label(): String = "Root"

    override suspend fun exec(cmd: String): ShellResult = withContext(Dispatchers.IO) {
        val r = Shell.cmd(cmd).exec()
        val out = (r.out + r.err).joinToString("\n").trim()
        ShellResult(r.isSuccess, out)
    }

    companion object {
        fun isAvailable(): Boolean = try {
            Shell.getShell().isRoot
        } catch (_: Throwable) {
            false
        }
    }
}
