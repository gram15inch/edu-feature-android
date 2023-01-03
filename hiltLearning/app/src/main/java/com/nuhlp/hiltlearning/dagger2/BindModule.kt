package com.nuhlp.hiltlearning.dagger2

import dagger.Binds
import dagger.Module


@Module
abstract class BindModule {
    @Binds
    abstract fun bindClassB(b: ClassB1): ClassB
}
