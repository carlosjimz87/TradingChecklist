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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.composables.ChecklistItemView
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

@Composable
fun ChecklistScreen() {
    val checklistItems = remember {
        mutableStateListOf(
            ChecklistItem("Am I focused?"),
            ChecklistItem("60m context clear?"),
            ChecklistItem("Zones marked?"),
            ChecklistItem("60m semaphore?"),
            ChecklistItem("1m semaphore?"),
            ChecklistItem("Big news?"),
            ChecklistItem("Risk combination worth it?"),
            ChecklistItem("Protected stop?"),
            ChecklistItem("Correct leverage?"),
            ChecklistItem("Entry zone clear?"),
            ChecklistItem("Trade management clear?"),
            ChecklistItem("No doubts?")
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("GOLD Strategy Checklist", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(checklistItems.size) { index ->
                    ChecklistItemView(item = checklistItems[index])
                }
            }
        }
    }
}