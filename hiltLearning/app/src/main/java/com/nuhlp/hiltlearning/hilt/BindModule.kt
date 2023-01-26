package com.nuhlp.hiltlearning.hilt


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class BindModule {
    @Binds
    abstract fun bindClassB(b: ClassB2): ClassB

}
