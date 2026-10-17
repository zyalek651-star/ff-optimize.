package com.cancer666.ffoptimize.model

data class Profile(
    val id: String,
    val name: String,
    val minRamGb: Int,
    val maxRamGb: Int,
    val minCores: Int,
    val socTiers: List<String>,
    val tweaks: List<String>,
    val expectedFpsDelta: Int
)

data class ProfileBundle(val version: Int, val profiles: List<Profile>)
