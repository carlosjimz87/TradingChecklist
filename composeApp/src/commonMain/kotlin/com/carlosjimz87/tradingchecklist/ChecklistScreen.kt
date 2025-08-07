package com.carlosjimz87.tradingchecklist

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.composables.ChecklistItemView
import com.carlosjimz87.tradingchecklist.data.ChecklistRepository
import com.carlosjimz87.tradingchecklist.data.ChecklistRepositoryImpl
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

@Composable
fun ChecklistScreen(repository: ChecklistRepository = ChecklistRepositoryImpl()) {
    var checklistItems by remember { mutableStateOf<List<ChecklistItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        checklistItems = repository.getChecklist()
    }

    val completed = checklistItems.count { it.checked }
    val progress = if (checklistItems.isNotEmpty()) {
        completed.toFloat() / checklistItems.size
    } else 0f

    var showResetDialog by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text(
                text = if (progress == 1f) "✔️ Well done, trader!" else "GOLD Strategy Checklist",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(16.dp))

            LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
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
                        ChecklistItemView(
                            item = item,
                            onCheckChange = { checked ->
                                checklistItems = checklistItems.toMutableList().apply {
                                    this[index] = this[index].copy(checked = checked)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // 🎉 Mensaje final opcional
            if (progress == 1f) {
                Text(
                    "🎉 Checklist completed. Time to execute like a pro!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF388E3C)
                )
                Spacer(Modifier.height(12.dp))
            }

            // 🔄 Botón de reset
            OutlinedButton(
                onClick = { showResetDialog = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Reset checklist")
            }
        }

        // 🧾 Diálogo de confirmación
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