package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.data.repos.StrategyRepository
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import kotlinx.coroutines.launch

@Composable
fun ChecklistListWithSave(
    restartKey: Int,
    checklistItems: List<ChecklistItem>,
    onChecklistChange: (List<ChecklistItem>) -> Unit,
    strategy: Strategy,
    repository: StrategyRepository,
    spacing: Dp = 8.dp
) {
    val coroutineScope = rememberCoroutineScope()

    key(restartKey) {
        ChecklistItemList(
            checklistItems = checklistItems,
            onItemChecked = { index, checked ->
                val updatedItems = checklistItems.toMutableList().apply {
                    this[index] = this[index].copy(checked = checked)
                }
                onChecklistChange(updatedItems)
                coroutineScope.launch {
                    repository.saveStrategy(strategy.copy(checklist = updatedItems))
                }
            },
            spacing = spacing
        )
    }
}