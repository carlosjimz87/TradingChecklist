package com.carlosjimz87.tradingchecklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.carlosjimz87.tradingchecklist.i18n.I18n
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initAndroidContext(applicationContext)

        I18n.load(getCurrentLocale())

        setContent {
            ChecklistScreen()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    ChecklistScreen()
}