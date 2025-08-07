package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

@Composable
fun ChecklistItemList(
    checklistItems: List<ChecklistItem>,
    onItemChecked: (index: Int, checked: Boolean) -> Unit,
    spacing: Dp = 8.dp
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(spacing),
        modifier = Modifier.fillMaxHeight()
    ) {
        items(checklistItems.size) { index ->
            val item = checklistItems[index]
            val bgColor by animateColorAsState(
                if (item.checked) Color(0xFFE6F4EA) else MaterialTheme.colorScheme.surface
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = bgColor)
            ) {
                ChecklistItem(
                    item = item,
                    onCheckChange = { checked ->
                        onItemChecked(index, checked)
                    }
                )
            }
        }
    }
}