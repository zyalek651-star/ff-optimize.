package com.cancer666.ffoptimize.model

data class TweakResult(
    val tweakId: String,
    val success: Boolean,
    val output: String,
    val durationMs: Long
)
