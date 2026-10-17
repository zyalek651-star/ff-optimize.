package com.cancer666.ffoptimize.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cancer666.ffoptimize.di.AppContainer
import com.cancer666.ffoptimize.domain.TweakExecutor
import com.cancer666.ffoptimize.model.DeviceSpec
import com.cancer666.ffoptimize.model.Profile
import com.cancer666.ffoptimize.model.Tweak
import com.cancer666.ffoptimize.shell.ShellProvider
import com.cancer666.ffoptimize.ui.components.StatusCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(container: AppContainer) {
    val scope = rememberCoroutineScope()
    var spec by remember { mutableStateOf<DeviceSpec?>(null) }
    var profile by remember { mutableStateOf<Profile?>(null) }
    var shell by remember { mutableStateOf<ShellProvider?>(null) }
    var busy by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val s = container.deviceRepository.read()
        val p = container.profileMatcher.match(s, container.profileRepository.load().profiles)
        val sh = container.refreshShell()
        spec = s
        profile = p
        shell = sh
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("FF Optimize", style = MaterialTheme.typography.headlineMedium)
        Text("Shell: ${shell?.label() ?: "..."}", style = MaterialTheme.typography.bodyMedium)

        spec?.let {
            StatusCard(
                "Device",
                "${it.manufacturer} ${it.model}\nRAM ${it.ramGb} GB · ${it.cores} cores · ${it.socTier} · ${it.abi}"
            )
        }

        profile?.let {
            StatusCard(
                "Recommended Profile",
                "${it.name} (${it.id})\nexpected FPS delta: +${it.expectedFpsDelta}",
                accent = true
            )
        }

        Button(
            onClick = {
                val p = profile ?: return@Button
                val sh = shell ?: return@Button
                scope.launch {
                    busy = true
                    status = "Applying ${p.name}..."
                    val tweaks: List<Tweak> = container.tweakRepository.load().tweaks.filter { it.id in p.tweaks }
                    val executor = TweakExecutor(sh, container.historyDatabase.historyDao())
                    val results = executor.apply(p.id, tweaks)
                    status = results.joinToString("\n") { "${it.tweakId}: ${if (it.success) "ok" else "fail"}" }
                    busy = false
                }
            },
            enabled = !busy && profile != null && shell?.label() != "Read-only",
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (busy) "Applying..." else "Apply Profile") }

        Button(
            onClick = {
                val p = profile ?: return@Button
                val sh = shell ?: return@Button
                scope.launch {
                    busy = true
                    status = "Reverting ${p.name}..."
                    val tweaks: List<Tweak> = container.tweakRepository.load().tweaks.filter { it.id in p.tweaks }
                    val executor = TweakExecutor(sh, container.historyDatabase.historyDao())
                    executor.apply(p.id, tweaks, revert = true)
                    status = "Reverted."
                    busy = false
                }
            },
            enabled = !busy && profile != null && shell?.label() != "Read-only",
            modifier = Modifier.fillMaxWidth()
        ) { Text("Revert") }

        if (status.isNotEmpty()) {
            StatusCard("Status", status)
        }
    }
}
