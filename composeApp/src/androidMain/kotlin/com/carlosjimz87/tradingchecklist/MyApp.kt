package com.carlosjimz87.tradingchecklist

import android.app.Application
import com.carlosjimz87.tradingchecklist.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApp)
        }
    }
}