package com.michaeludjiawan.githubfinder

import android.app.Application
import com.michaeludjiawan.githubfinder.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    networkModule
                )
            )
        }
    }
}