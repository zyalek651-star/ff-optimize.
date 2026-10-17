package com.cancer666.ffoptimize

import android.app.Application
import com.cancer666.ffoptimize.di.AppContainer

class FFApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
