package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.ui.theme.AppColors

@Composable
fun EmptyListMessage(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Icon(
//                imageVector = Icons.Default.Warning,
//                contentDescription = "No checklist",
//                tint = AppColors.OnSurface.copy(alpha = 0.5f),
//                modifier = Modifier.size(64.dp)
//            )
//            Spacer(Modifier.height(12.dp))
            Text(
                text = "No checklist available.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.OnSurface.copy(alpha = 0.6f)
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
            ) {
                Text("Retry")
            }
        }
    }
}