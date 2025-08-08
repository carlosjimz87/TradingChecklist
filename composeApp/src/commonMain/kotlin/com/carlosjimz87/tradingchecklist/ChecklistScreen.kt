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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.composables.ChecklistListWithSave
import com.carlosjimz87.tradingchecklist.composables.ChecklistProgress
import com.carlosjimz87.tradingchecklist.composables.EmptyListMessage
import com.carlosjimz87.tradingchecklist.composables.PagerIndicator
import com.carlosjimz87.tradingchecklist.data.repos.StrategyRepository
import com.carlosjimz87.tradingchecklist.i18n.I18n
import com.carlosjimz87.tradingchecklist.ui.theme.AppColors
import getColor
import kotlinx.coroutines.launch

@Composable
fun ChecklistScreen(
    repository: StrategyRepository = inject()
) {
    val coroutineScope = rememberCoroutineScope()
    val strategies by produceState(initialValue = emptyList(), repository) {
        value = repository.getStrategies()
    }

    val pagerState = rememberPagerState(pageCount = { strategies.size })
    var showResetDialog by remember { mutableStateOf(false) }
    var restartKey by remember { mutableStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val currentPage by remember {
        derivedStateOf { pagerState.currentPage }
    }.let { derived ->
        snapshotFlow { derived.value }.collectAsState(initial = 0)
    }
    var wasManuallyReset by remember { mutableStateOf(false) }

    if (strategies.isEmpty()) {
        EmptyListMessage {
            coroutineScope.launch {
                strategies.toMutableList().addAll(repository.getStrategies())
            }
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            val strategy = strategies[page]

            var checklistItems by remember(restartKey, page) {
                mutableStateOf(strategy.checklist)
            }

            LaunchedEffect(restartKey, page) {
                val updatedStrategies = repository.getStrategies()
                checklistItems = updatedStrategies.firstOrNull { it.id == strategy.id }?.checklist
                    ?: strategy.checklist
            }

            val completed = checklistItems.count { it.checked }
            val progress =
                if (checklistItems.isNotEmpty()) completed.toFloat() / checklistItems.size else 0f

            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val isCompact = maxWidth < 600.dp

                Scaffold(
                    snackbarHost = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = if (isCompact) Alignment.BottomCenter else Alignment.BottomEnd
                        ) {
                            Box(
                                modifier = Modifier
                                    .then(
                                        if (isCompact) Modifier.fillMaxWidth()
                                        else Modifier
                                            .fillMaxWidth(0.5f)
                                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                                    )
                            ) {
                                SnackbarHost(
                                    hostState = snackbarHostState,
                                    modifier = Modifier.widthIn(max = 400.dp),
                                ) { data ->
                                    Snackbar(
                                        containerColor = AppColors.Success.copy(alpha = 0.95f),
                                        contentColor = Color.White,
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(data.visuals.message)
                                    }
                                }
                            }
                        }
                    }
                ) { padding ->
                    val horizontalPadding = if (isCompact) 16.dp else 48.dp

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = strategy.getColor()
                    ) {
                        if (isCompact) {
                            Column(
                                modifier = Modifier
                                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                                    .padding(horizontal = horizontalPadding, vertical = 24.dp)
                                    .fillMaxSize()
                            ) {
                                key(restartKey) {
                                    ChecklistProgress(
                                        title = strategy.name,
                                        progress = progress,
                                        isCompact = true,
                                        onReset = { showResetDialog = true },
                                        snackbarHostState = snackbarHostState
                                    )
                                }

                                Spacer(Modifier.height(16.dp))

                                key(restartKey) {
                                    ChecklistListWithSave(
                                        checklistItems = checklistItems,
                                        onChecklistChange = { checklistItems = it },
                                        strategy = strategy,
                                        repository = repository
                                    )
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                                    .padding(horizontal = horizontalPadding, vertical = 24.dp)
                                    .fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    key(restartKey) {
                                        ChecklistListWithSave(
                                            checklistItems = checklistItems,
                                            onChecklistChange = { checklistItems = it },
                                            strategy = strategy,
                                            repository = repository
                                        )
                                    }
                                }

                                Spacer(Modifier.width(48.dp))

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    key(restartKey) {
                                        ChecklistProgress(
                                            title = strategy.name,
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

                    if (showResetDialog) {
                        AlertDialog(
                            onDismissRequest = { showResetDialog = false },
                            title = {
                                Text(text = I18n.strings.reset_button, color = AppColors.OnSurface)
                            },
                            text = {
                                Text(text = I18n.strings.confirm_reset, color = AppColors.OnSurface)
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        checklistItems =
                                            checklistItems.map { it.copy(checked = false) }
                                        coroutineScope.launch {
                                            repository.resetStrategy(strategy.id)
                                        }
                                        wasManuallyReset = true
                                        restartKey++
                                        showResetDialog = false
                                    },
                                    colors = ButtonDefaults.textButtonColors(contentColor = AppColors.Primary)
                                ) { Text("Yes") }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { showResetDialog = false },
                                    colors = ButtonDefaults.textButtonColors(contentColor = AppColors.Primary)
                                ) { Text("Cancel") }
                            },
                            containerColor = Color.White
                        )
                    }
                }
            }
        }

        PagerIndicator(
            pageCount = strategies.size,
            currentPage = currentPage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp),
            backgroundColor = strategies.getOrNull(currentPage)?.getColor() ?: Color.White
        ) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
            }
        }
    }
}