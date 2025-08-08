package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.carlosjimz87.tradingchecklist.data.repos.StrategyRepository
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import kotlinx.coroutines.launch

@Composable
fun ChecklistListWithSave(
    checklistItems: List<ChecklistItem>,
    onChecklistChange: (List<ChecklistItem>) -> Unit,
    strategy: Strategy,
    repository: StrategyRepository
) {
    val coroutineScope = rememberCoroutineScope()

    ChecklistItemList(
        checklistItems = checklistItems,
        onItemChecked = { index, checked ->
            val updated = checklistItems.toMutableList().apply {
                this[index] = this[index].copy(checked = checked)
            }
            onChecklistChange(updated)
            coroutineScope.launch {
                repository.saveStrategy(strategy.copy(checklist = updated))
            }
        }
    )
}