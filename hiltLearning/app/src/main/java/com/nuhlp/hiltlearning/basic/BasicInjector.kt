package com.nuhlp.hiltlearning.basic

class BasicInjector() {
    fun createA(b: ClassB) = ClassA(b)
    fun createB() = ClassB1()
}