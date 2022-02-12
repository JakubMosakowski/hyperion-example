package com.jakmos.hyperionexample

import android.app.Application
import timber.log.Timber

class HyperionApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}
