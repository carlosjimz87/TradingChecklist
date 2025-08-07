package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ChecklistProgress(progress: Float, isCompact: Boolean, onReset: () -> Unit) {
    val textStyle =
        if (isCompact) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineMedium

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "GOLD Checklist",
            style = textStyle
        )

        Spacer(Modifier.height(16.dp))

        if (isCompact) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.weight(1f),
                    color = ProgressIndicatorDefaults.linearColor,
                    trackColor = ProgressIndicatorDefaults.linearTrackColor,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "${(progress * 100).roundToInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.widthIn(min = 40.dp),
                    textAlign = TextAlign.End
                )
            }
        } else {
            Box(modifier = Modifier.size(160.dp)) {
                CircularProgressIndicator(
                    progress = { progress },
                    strokeWidth = 10.dp,
                    color = Color(0xFF7C4DFF),
                    modifier = Modifier.fillMaxSize()
)
                Text(
                    text = "${(progress * 100).roundToInt()}%",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        if (progress == 1f) {
            Text(
                text = "✅ Checklist completed!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF388E3C)
            )
            Spacer(Modifier.height(12.dp))
        }

        OutlinedButton(onClick = onReset) {
            Text("Reset checklist")
        }
    }
}