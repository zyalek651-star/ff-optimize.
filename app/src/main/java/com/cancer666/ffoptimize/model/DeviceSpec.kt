package com.cancer666.ffoptimize.model

data class DeviceSpec(
    val model: String,
    val manufacturer: String,
    val androidVersion: Int,
    val ramGb: Int,
    val cores: Int,
    val socTier: SocTier,
    val abi: String
)

enum class SocTier { ENTRY, MID, FLAGSHIP }
