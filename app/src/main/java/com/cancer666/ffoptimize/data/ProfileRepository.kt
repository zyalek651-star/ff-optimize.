package com.cancer666.ffoptimize.data

import com.cancer666.ffoptimize.config.ConfigLoader
import com.cancer666.ffoptimize.model.ProfileBundle

class ProfileRepository(private val loader: ConfigLoader) {
    fun load(): ProfileBundle = loader.loadProfiles()
}
