package com.semicolon.highball.di

import android.app.Application
import android.os.StrictMode
import com.semicolon.highball.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                remoteModule,
                localModule,
                viewModelModule
            )
        }
    }
}