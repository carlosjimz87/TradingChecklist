package com.carlosjimz87.tradingchecklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("GOLD Strategy Checklist", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(checklistItems.size) { index ->
                    ChecklistItemView(
                        item = checklistItems[index],
                        onCheckChange = { checked ->
                            checklistItems = checklistItems.toMutableList().apply {
                                this[index] = this[index].copy(checked = checked)
                            }
                        }
                    )
                }
            }
        }
    }
}