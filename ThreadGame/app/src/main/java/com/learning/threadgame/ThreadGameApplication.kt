package com.learning.threadgame

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class ThreadGameApplication:Application() {
    lateinit var context : Context
    override fun onCreate() {
        super.onCreate()
        context= this
        Timber.plant(Timber.DebugTree())
    }

}