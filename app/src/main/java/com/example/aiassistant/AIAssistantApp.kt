package com.example.aiassistant

import android.app.Application
import com.example.aiassistant.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AIAssistantApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@AIAssistantApp)
            modules(appModule)
        }
    }
} 