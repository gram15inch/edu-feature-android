package com.nuhlp.hiltlearning.dagger2

import com.nuhlp.hiltlearning.basic.ClassB
import javax.inject.Inject

class ClassA @Inject constructor(private val b: ClassB) {
    fun executeA() {
        b.executeB()
    }
}
class ClassB1 @Inject constructor() : ClassB {
    override fun executeB() {
        // Do something
    }
}
class ClassB2 @Inject constructor() : ClassB {
    override fun executeB() {
        // Do something else
    }
}
