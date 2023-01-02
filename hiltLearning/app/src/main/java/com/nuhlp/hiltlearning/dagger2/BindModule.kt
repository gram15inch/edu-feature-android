package com.nuhlp.hiltlearning.dagger2

import com.nuhlp.hiltlearning.basic.ClassB
import dagger.Binds
import dagger.Module


@Module
abstract class BindModule {
    @Binds
    abstract fun bindClassB(b: ClassB1): ClassB
}
