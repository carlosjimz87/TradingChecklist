package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

@Composable
fun ChecklistItem(
    item: ChecklistItem,
    onCheckChange: (Boolean) -> Unit
) {
    val transition = updateTransition(targetState = item.checked, label = "CheckTransition")

    val fillProgress by transition.animateFloat(
        label = "FillProgress"
    ) { if (it) 1f else 0f }

    val backgroundColor = Color(0xFFE6F4EA)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckChange(!item.checked) }
            .height(IntrinsicSize.Min),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            // Fondo animado con Canvas desde la derecha
            Canvas(modifier = Modifier.matchParentSize()) {
                val width = size.width * fillProgress
                drawRect(
                    color = backgroundColor,
                    topLeft = androidx.compose.ui.geometry.Offset(size.width - width, 0f),
                    size = androidx.compose.ui.geometry.Size(width, size.height)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = item.checked,
                    onCheckedChange = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}