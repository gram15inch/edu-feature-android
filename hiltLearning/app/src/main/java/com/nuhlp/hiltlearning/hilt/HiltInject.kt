package com.nuhlp.hiltlearning.hilt

import timber.log.Timber
import javax.inject.Inject

class ClassA @Inject constructor(private val b: ClassB) {
    fun executeA() {
        b.executeB()
    }
}
interface ClassB {
    fun executeB()
}
class ClassB1 @Inject constructor() : ClassB {
    override fun executeB() {
        Timber.d("call hilt B1")
    }
}
class ClassB2 @Inject constructor() : ClassB {
    override fun executeB() {
        Timber.d("call hilt B2")
    }
}
