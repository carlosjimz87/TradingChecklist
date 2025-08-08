package com.carlosjimz87.tradingchecklist.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosjimz87.tradingchecklist.i18n.I18n
import com.carlosjimz87.tradingchecklist.ui.theme.AppColors
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ChecklistProgress(
    title: String,
    progress: Float,
    isCompact: Boolean,
    onReset: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val showProgress = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val trackColor = if (progress == 1f) AppColors.Success else AppColors.Primary

    LaunchedEffect(Unit) {
        showProgress.value = true
    }

    LaunchedEffect(progress) {
        showProgress.value = true
        if (progress == 1f) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = I18n.strings.completed_message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    AnimatedVisibility(
        visible = showProgress.value,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(durationMillis = 400)),
        exit = ExitTransition.None
    ) {
        val textStyle =
            if (isCompact) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineMedium

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = textStyle
            )

            Spacer(Modifier.height(16.dp))

            if (isCompact) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.weight(1f),
                        color = trackColor,
                        trackColor = AppColors.PrimaryLight,
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "${(progress * 100).roundToInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.widthIn(min = 40.dp),
                        textAlign = TextAlign.End
                    )
                }
            } else {
                Box(modifier = Modifier.size(160.dp)) {
                    CircularProgressIndicator(
                        progress = { progress },
                        strokeWidth = 10.dp,
                        color = trackColor,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "${(progress * 100).roundToInt()}%",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = onReset,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColors.Primary
                ),
                border = BorderStroke(1.dp, AppColors.Primary)
            ) {
                Text(I18n.strings.reset_button)
            }
        }
    }
}