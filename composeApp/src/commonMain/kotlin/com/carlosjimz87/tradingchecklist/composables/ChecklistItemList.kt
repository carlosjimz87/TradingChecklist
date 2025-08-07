package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        itemsIndexed(checklistItems) { index, item ->
            ChecklistItem(
                item = item,
                onCheckChange = { checked ->
                    onItemChecked(index, checked)
                }
            )
        }
    }
}