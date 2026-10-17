package com.cancer666.ffoptimize.data

import com.cancer666.ffoptimize.config.ConfigLoader
import com.cancer666.ffoptimize.model.TweakBundle

class TweakRepository(private val loader: ConfigLoader) {
    fun load(): TweakBundle = loader.loadTweaks()
}
