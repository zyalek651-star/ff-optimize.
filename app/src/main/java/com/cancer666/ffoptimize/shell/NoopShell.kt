package com.cancer666.ffoptimize.shell

class NoopShell : ShellProvider {
    override fun label(): String = "Read-only"
    override suspend fun exec(cmd: String): ShellResult =
        ShellResult(false, "shell unavailable; command skipped: $cmd")
}
