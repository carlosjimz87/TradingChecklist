package com.carlosjimz87.tradingchecklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.composables.ChecklistItemList
import com.carlosjimz87.tradingchecklist.composables.ChecklistProgress
import com.carlosjimz87.tradingchecklist.data.ChecklistRepository
import com.carlosjimz87.tradingchecklist.data.ChecklistRepositoryImpl
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

@Composable
fun ChecklistScreen(repository: ChecklistRepository = ChecklistRepositoryImpl()) {
    var checklistItems by remember { mutableStateOf<List<ChecklistItem>>(emptyList()) }
    var showResetDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        checklistItems = repository.getChecklist()
    }

    val completed = checklistItems.count { it.checked }
    val progress =
        if (checklistItems.isNotEmpty()) completed.toFloat() / checklistItems.size else 0f

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isCompact = maxWidth < 600.dp
        val horizontalPadding = if (isCompact) 16.dp else 48.dp

        Surface(modifier = Modifier.fillMaxSize()) {
            if (isCompact) {
                // ✅ Mobile layout
                Column(
                    modifier = Modifier
                        .padding(WindowInsets.safeDrawing.asPaddingValues())
                        .padding(horizontal = horizontalPadding, vertical = 24.dp)
                        .fillMaxSize()
                ) {
                    ChecklistProgress(
                        progress = progress,
                        isCompact = true,
                        onReset = { showResetDialog = true },
                    )

                    Spacer(Modifier.height(16.dp))

                    ChecklistItemList(
                        checklistItems = checklistItems,
                        onItemChecked = { index, checked ->
                            checklistItems = checklistItems.toMutableList().apply {
                                this[index] = this[index].copy(checked = checked)
                            }
                        }
                    )
                }

            } else {
                // ✅ Tablet/Desktop layout
                Row(
                    modifier = Modifier
                        .padding(WindowInsets.safeDrawing.asPaddingValues())
                        .padding(horizontal = horizontalPadding, vertical = 24.dp)
                        .fillMaxSize()
                ) {
                    // Checklist (left column)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        ChecklistItemList(
                            checklistItems = checklistItems,
                            onItemChecked = { index, checked ->
                                checklistItems = checklistItems.toMutableList().apply {
                                    this[index] = this[index].copy(checked = checked)
                                }
                            },
                            spacing = 12.dp
                        )
                    }

                    Spacer(Modifier.width(48.dp))

                    // Right column: header + progress + actions
                    Box(
                        modifier = Modifier
                            .weight(1f) // Igual peso que la izquierda
                            .fillMaxHeight()
                    ) {
                        ChecklistProgress(
                            progress = progress,
                            isCompact = false,
                            onReset = { showResetDialog = true }
                        )
                    }
                }
            }
        }

        // Dialog
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text("Reset checklist?") },
                text = { Text("Are you sure you want to uncheck all items?") },
                confirmButton = {
                    TextButton(onClick = {
                        checklistItems = checklistItems.map { it.copy(checked = false) }
                        showResetDialog = false
                    }) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}