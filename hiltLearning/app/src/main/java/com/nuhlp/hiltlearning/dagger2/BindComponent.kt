package com.nuhlp.hiltlearning.dagger2

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BindModule::class])
interface BindComponent