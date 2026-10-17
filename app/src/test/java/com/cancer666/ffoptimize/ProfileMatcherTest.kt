package com.cancer666.ffoptimize

import com.cancer666.ffoptimize.domain.ProfileMatcher
import com.cancer666.ffoptimize.model.DeviceSpec
import com.cancer666.ffoptimize.model.Profile
import com.cancer666.ffoptimize.model.SocTier
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileMatcherTest {
    private val profiles = listOf(
        Profile("low", "Low", 2, 4, 4, listOf("entry"), listOf("gfx_low"), 8),
        Profile("mid", "Mid", 4, 8, 6, listOf("mid"), listOf("gfx_balanced"), 5),
        Profile("high", "High", 8, 16, 8, listOf("flagship"), listOf("gfx_smooth"), 3)
    )

    @Test fun `low end matches low`() {
        val s = DeviceSpec("M", "M", 30, 3, 4, SocTier.ENTRY, "arm64-v8a")
        assertEquals("low", ProfileMatcher().match(s, profiles)?.id)
    }

    @Test fun `mid matches mid`() {
        val s = DeviceSpec("M", "M", 33, 6, 8, SocTier.MID, "arm64-v8a")
        assertEquals("mid", ProfileMatcher().match(s, profiles)?.id)
    }

    @Test fun `flagship matches high`() {
        val s = DeviceSpec("M", "M", 34, 12, 8, SocTier.FLAGSHIP, "arm64-v8a")
        assertEquals("high", ProfileMatcher().match(s, profiles)?.id)
    }
}
