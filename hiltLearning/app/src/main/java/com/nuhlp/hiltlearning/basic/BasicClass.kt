package com.nuhlp.hiltlearning.basic

import timber.log.Timber


class ClassA(val b: ClassB) {
    fun executeA() {
        b.executeB()
    }
}
interface ClassB {
    fun executeB()
}
class ClassB1() : ClassB {
    override fun executeB() {
        Timber.d("call basic B1")
    }
}
class ClassB2() : ClassB {
    override fun executeB() {
        Timber.d("call basic B2")
    }
}

