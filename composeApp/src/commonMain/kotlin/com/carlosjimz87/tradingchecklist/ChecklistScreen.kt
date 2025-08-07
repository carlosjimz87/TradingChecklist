package com.carlosjimz87.tradingchecklist

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.composables.ChecklistItemList
import com.carlosjimz87.tradingchecklist.composables.ChecklistProgress
import com.carlosjimz87.tradingchecklist.data.ChecklistRepository
import com.carlosjimz87.tradingchecklist.data.ChecklistRepositoryImpl
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.i18n.I18n
import com.carlosjimz87.tradingchecklist.ui.theme.AppColors

@Composable
fun ChecklistScreen(repository: ChecklistRepository = ChecklistRepositoryImpl()) {
    var checklistItems by remember { mutableStateOf<List<ChecklistItem>>(emptyList()) }
    var showResetDialog by remember { mutableStateOf(false) }
    var restartKey by remember { mutableStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        checklistItems = repository.getChecklist()
    }

    val completed = checklistItems.count { it.checked }
    val progress =
        if (checklistItems.isNotEmpty()) completed.toFloat() / checklistItems.size else 0f

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            containerColor = AppColors.Success.copy(alpha = 0.95f),
                            contentColor = Color.White,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(data.visuals.message)
                        }
                    }
                )
            }
        ) { padding ->
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
                        key(restartKey) {
                            ChecklistProgress(
                                progress = progress,
                                isCompact = true,
                                onReset = { showResetDialog = true },
                                snackbarHostState = snackbarHostState
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        key(restartKey) {
                            ChecklistItemList(
                                checklistItems = checklistItems,
                                onItemChecked = { index, checked ->
                                    checklistItems = checklistItems.toMutableList().apply {
                                        this[index] = this[index].copy(checked = checked)
                                    }
                                }
                            )
                        }
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
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            key(restartKey) {
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
                        }

                        Spacer(Modifier.width(48.dp))

                        // Right column: header + progress + actions
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            key(restartKey) {
                                ChecklistProgress(
                                    progress = progress,
                                    isCompact = false,
                                    onReset = { showResetDialog = true },
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        }
                    }
                }
            }

            // Dialog
            if (showResetDialog) {
                AlertDialog(
                    onDismissRequest = { showResetDialog = false },
                    title = {
                        Text(
                            text = I18n.strings.reset_button,
                            color = AppColors.OnSurface
                        )
                    },
                    text = {
                        Text(
                            text = I18n.strings.confirm_reset,
                            color = AppColors.OnSurface
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                checklistItems = checklistItems.map { it.copy(checked = false) }
                                restartKey++ // Force recompose
                                showResetDialog = false
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = AppColors.Primary)
                        ) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showResetDialog = false },
                            colors = ButtonDefaults.textButtonColors(contentColor = AppColors.Primary)
                        ) {
                            Text("Cancel")
                        }
                    },
                    containerColor = Color.White
                )
            }
        }
    }
}