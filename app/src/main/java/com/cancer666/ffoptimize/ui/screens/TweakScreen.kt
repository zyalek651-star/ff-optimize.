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
import com.cancer666.ffoptimize.model.Tweak
import com.cancer666.ffoptimize.ui.components.StatusCard

@Composable
fun TweakScreen(container: AppContainer) {
    var tweaks by remember { mutableStateOf<List<Tweak>>(emptyList()) }
    LaunchedEffect(Unit) { tweaks = container.tweakRepository.load().tweaks }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tweaks) { t ->
            StatusCard(
                t.name,
                "${t.description}\nroot=${t.requiresRoot} shizuku=${t.requiresShizuku}\ncmds=${t.commands.size} revert=${t.revert.size}"
            )
        }
    }
}
