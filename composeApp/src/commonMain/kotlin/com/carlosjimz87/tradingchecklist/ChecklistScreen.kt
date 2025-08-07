package com.carlosjimz87.tradingchecklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 1. SHARED UI ENTRY POINT
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

// 2. ITEM MODEL
data class ChecklistItem(val title: String, var checked: Boolean = false)

// 3. ITEM VIEW
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

// 4. PLATFORM-SPECIFIC ENTRY POINTS
//fun getDesktopApp() = application {
//    Window(onCloseRequest = ::exitApplication, title = "Trading Checklist") {
//        ChecklistScreen()
//    }
//}
//
//fun getIOSApp() = ComposeUIViewController { ChecklistScreen() }