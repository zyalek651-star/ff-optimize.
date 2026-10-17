package com.cancer666.ffoptimize.di

import android.content.Context
import com.cancer666.ffoptimize.config.ConfigLoader
import com.cancer666.ffoptimize.data.DeviceRepository
import com.cancer666.ffoptimize.data.HistoryDatabase
import com.cancer666.ffoptimize.data.ProfileRepository
import com.cancer666.ffoptimize.data.TweakRepository
import com.cancer666.ffoptimize.domain.ProfileMatcher
import com.cancer666.ffoptimize.domain.TweakExecutor
import com.cancer666.ffoptimize.shell.NoopShell
import com.cancer666.ffoptimize.shell.RootShell
import com.cancer666.ffoptimize.shell.ShellProvider
import com.cancer666.ffoptimize.shell.ShizukuShell

class AppContainer(private val context: Context) {

    private val configLoader: ConfigLoader by lazy { ConfigLoader(context.assets) }

    val profileRepository: ProfileRepository by lazy { ProfileRepository(configLoader) }
    val tweakRepository: TweakRepository by lazy { TweakRepository(configLoader) }
    val deviceRepository: DeviceRepository by lazy { DeviceRepository(context) }
    val historyDatabase: HistoryDatabase by lazy { HistoryDatabase.get(context) }

    fun shellProvider(): ShellProvider {
        if (ShizukuShell.isAvailable()) return ShizukuShell(context)
        if (RootShell.isAvailable()) return RootShell()
        return NoopShell()
    }

    val profileMatcher: ProfileMatcher by lazy { ProfileMatcher() }

    fun tweakExecutor(shell: ShellProvider): TweakExecutor =
        TweakExecutor(shell, historyDatabase.historyDao())

    fun refreshShell(): ShellProvider = shellProvider()
}
