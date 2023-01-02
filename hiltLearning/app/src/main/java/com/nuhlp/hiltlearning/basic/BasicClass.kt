package com.nuhlp.hiltlearning.basic


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
        // Do something
    }
}
class ClassB2() : ClassB {
    override fun executeB() {
        // Do something else
    }
}

