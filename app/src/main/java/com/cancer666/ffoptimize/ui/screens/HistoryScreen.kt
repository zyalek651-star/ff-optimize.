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
import com.cancer666.ffoptimize.data.HistoryEntry
import com.cancer666.ffoptimize.di.AppContainer
import com.cancer666.ffoptimize.ui.components.StatusCard

@Composable
fun HistoryScreen(container: AppContainer) {
    var entries by remember { mutableStateOf<List<HistoryEntry>>(emptyList()) }
    LaunchedEffect(Unit) {
        container.historyDatabase.historyDao().recent().collect { entries = it }
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(entries) { e ->
            StatusCard(
                "${e.profileId} · ${e.tweakId}",
                "${if (e.success) "OK" else "FAIL"} · ${e.durationMs}ms\n${e.output.take(160)}"
            )
        }
    }
}
