package com.michaeludjiawan.githubfinder

import android.app.Application
import com.michaeludjiawan.githubfinder.di.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApplication : Application() {

    override fun onCreate() {
        startKoin {
            androidContext(this@TestApplication)
            modules(listOf(featureModule))
        }
    }

}