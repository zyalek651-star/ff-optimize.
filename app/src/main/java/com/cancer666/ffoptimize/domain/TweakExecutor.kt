package com.cancer666.ffoptimize.domain

import com.cancer666.ffoptimize.data.HistoryDao
import com.cancer666.ffoptimize.data.HistoryEntry
import com.cancer666.ffoptimize.model.Tweak
import com.cancer666.ffoptimize.model.TweakResult
import com.cancer666.ffoptimize.shell.ShellProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TweakExecutor(
    private val shell: ShellProvider,
    private val historyDao: HistoryDao
) {
    suspend fun apply(profileId: String, tweaks: List<Tweak>, revert: Boolean = false): List<TweakResult> =
        withContext(Dispatchers.IO) {
            val results = mutableListOf<TweakResult>()
            for (t in tweaks) {
                val cmds = if (revert) t.revert else t.commands
                if (cmds.isEmpty()) {
                    results += TweakResult(t.id, true, "no-op", 0)
                    continue
                }
                val start = System.currentTimeMillis()
                var out = ""
                var ok = true
                for (cmd in cmds) {
                    val r = shell.exec(cmd)
                    out += r.output
                    if (!r.success) { ok = false; break }
                }
                val dur = System.currentTimeMillis() - start
                val result = TweakResult(t.id, ok, out, dur)
                results += result
                historyDao.insert(
                    HistoryEntry(
                        timestamp = System.currentTimeMillis(),
                        profileId = profileId,
                        tweakId = t.id,
                        success = ok,
                        output = out,
                        durationMs = dur
                    )
                )
            }
            results
        }
}
