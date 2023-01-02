package com.nuhlp.hiltlearning.dagger2

import com.nuhlp.hiltlearning.ui.DaggerActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@ActivityScope
@Subcomponent(modules = [BindModule::class])
interface MainSubcomponent {
    fun inject(daggerActivity: DaggerActivity)
}

@Singleton
@Component
interface BindComponent{
    fun createMainSubComponent(): MainSubcomponent
}