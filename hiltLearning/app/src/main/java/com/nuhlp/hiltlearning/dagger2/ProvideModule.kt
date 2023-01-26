package com.nuhlp.hiltlearning.dagger2

import com.nuhlp.hiltlearning.basic.ClassA
import com.nuhlp.hiltlearning.basic.ClassB
import com.nuhlp.hiltlearning.basic.ClassB1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProvideModule {
    @Provides
    fun provideClassA(b: ClassB): ClassA = ClassA(b)
    @Provides
    fun provideClassB(): ClassB = ClassB1()
}
