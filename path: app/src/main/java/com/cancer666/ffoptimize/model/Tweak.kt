package com.cancer666.ffoptimize.model

data class Tweak(
    val id: String,
    val name: String,
    val description: String,
    val requiresRoot: Boolean,
    val requiresShizuku: Boolean,
    val commands: List<String>,
    val revert: List<String>
)

data class TweakBundle(val version: Int, val tweaks: List<Tweak>)
