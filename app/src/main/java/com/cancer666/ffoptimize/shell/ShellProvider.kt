package com.cancer666.ffoptimize.shell

data class ShellResult(val success: Boolean, val output: String)

interface ShellProvider {
    fun label(): String
    suspend fun exec(cmd: String): ShellResult
}
