package com.cancer666.ffoptimize.domain

import com.cancer666.ffoptimize.model.DeviceSpec
import com.cancer666.ffoptimize.model.Profile
import com.cancer666.ffoptimize.model.SocTier
import kotlin.math.abs

class ProfileMatcher {
    fun match(spec: DeviceSpec, profiles: List<Profile>): Profile? {
        val tier = when (spec.socTier) {
            SocTier.ENTRY -> "entry"
            SocTier.MID -> "mid"
            SocTier.FLAGSHIP -> "flagship"
        }
        return profiles
            .filter { spec.ramGb in it.minRamGb..it.maxRamGb }
            .filter { spec.cores >= it.minCores }
            .filter { it.socTiers.contains(tier) }
            .minByOrNull { abs(it.minRamGb - spec.ramGb) }
            ?: profiles.minByOrNull { abs(it.minRamGb - spec.ramGb) }
    }
}
