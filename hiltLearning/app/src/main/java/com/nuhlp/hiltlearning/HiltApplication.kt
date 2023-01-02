package com.nuhlp.hiltlearning

import android.app.Application
import com.nuhlp.hiltlearning.dagger2.BindComponent
import com.nuhlp.hiltlearning.dagger2.DaggerBindComponent
import timber.log.Timber

class HiltApplication: Application() {
    lateinit var component: BindComponent
    override fun onCreate() {
        super.onCreate()
        component = DaggerBindComponent.create()
        Timber.plant(Timber.DebugTree())
    }
}

