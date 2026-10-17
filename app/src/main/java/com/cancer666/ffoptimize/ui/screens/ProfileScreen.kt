package com.cancer666.ffoptimize.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.cancer666.ffoptimize.di.AppContainer
import com.cancer666.ffoptimize.model.Profile
import com.cancer666.ffoptimize.ui.components.StatusCard

@Composable
fun ProfileScreen(container: AppContainer) {
    var profiles by remember { mutableStateOf<List<Profile>>(emptyList()) }
    LaunchedEffect(Unit) { profiles = container.profileRepository.load().profiles }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(profiles) { p ->
            StatusCard(
                p.name,
                "id=${p.id}\nRAM ${p.minRamGb}-${p.maxRamGb} GB · cores≥${p.minCores}\ntweaks: ${p.tweaks.joinToString()}\nfps +${p.expectedFpsDelta}"
            )
        }
    }
}
