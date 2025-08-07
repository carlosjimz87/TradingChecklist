package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

@Composable
fun ChecklistItemView(item: ChecklistItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = item.checked,
                onValueChange = { item.checked = it }
            )
            .padding(8.dp)
    ) {
        Checkbox(
            checked = item.checked,
            onCheckedChange = null // handled by Row toggleable
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
    }
}