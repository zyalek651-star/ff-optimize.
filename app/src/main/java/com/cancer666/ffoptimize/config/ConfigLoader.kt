package com.cancer666.ffoptimize.config

import android.content.res.AssetManager
import com.cancer666.ffoptimize.model.Profile
import com.cancer666.ffoptimize.model.ProfileBundle
import com.cancer666.ffoptimize.model.Tweak
import com.cancer666.ffoptimize.model.TweakBundle
import org.json.JSONArray
import org.json.JSONObject

class ConfigLoader(private val assets: AssetManager) {

    fun loadProfiles(): ProfileBundle {
        val raw = assets.open(AppConfig.ASSET_PROFILES).bufferedReader().use { it.readText() }
        val root = JSONObject(raw)
        val arr = root.getJSONArray("profiles")
        val list = mutableListOf<Profile>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            list += Profile(
                id = o.getString("id"),
                name = o.getString("name"),
                minRamGb = o.getInt("minRamGb"),
                maxRamGb = o.getInt("maxRamGb"),
                minCores = o.getInt("minCores"),
                socTiers = o.getJSONArray("socTiers").toStringList(),
                tweaks = o.getJSONArray("tweaks").toStringList(),
                expectedFpsDelta = o.optInt("expectedFpsDelta", 0)
            )
        }
        return ProfileBundle(root.optInt("version", 1), list)
    }

    fun loadTweaks(): TweakBundle {
        val raw = assets.open(AppConfig.ASSET_TWEAKS).bufferedReader().use { it.readText() }
        val root = JSONObject(raw)
        val arr = root.getJSONArray("tweaks")
        val list = mutableListOf<Tweak>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            list += Tweak(
                id = o.getString("id"),
                name = o.getString("name"),
                description = o.optString("description", ""),
                requiresRoot = o.optBoolean("requiresRoot", false),
                requiresShizuku = o.optBoolean("requiresShizuku", false),
                commands = o.getJSONArray("commands").toStringList(),
                revert = o.optJSONArray("revert")?.toStringList() ?: emptyList()
            )
        }
        return TweakBundle(root.optInt("version", 1), list)
    }
}

private fun JSONArray.toStringList(): List<String> {
    val out = mutableListOf<String>()
    for (i in 0 until length()) out += getString(i)
    return out
}
