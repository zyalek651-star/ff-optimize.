package com.cancer666.ffoptimize.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cancer666.ffoptimize.ui.theme.Accent
import com.cancer666.ffoptimize.ui.theme.Surface
import com.cancer666.ffoptimize.ui.theme.TextMuted
import com.cancer666.ffoptimize.ui.theme.TextPrimary

@Composable
fun StatusCard(title: String, body: String, accent: Boolean = false) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Surface)
            .padding(16.dp)
    ) {
        Text(title, color = TextMuted, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(6.dp))
        Text(body, color = if (accent) Accent else TextPrimary, fontWeight = FontWeight.SemiBold)
    }
}
