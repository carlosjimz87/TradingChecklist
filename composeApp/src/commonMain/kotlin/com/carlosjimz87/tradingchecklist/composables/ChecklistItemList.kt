package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import kotlinx.coroutines.delay

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

            val visible = remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(index * 50L) // Delay incremental por ítem
                visible.value = true
            }

            AnimatedVisibility(
                visible = visible.value,
                enter = slideInHorizontally(
                    initialOffsetX = { -it / 2 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(300)),
                exit = ExitTransition.None
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