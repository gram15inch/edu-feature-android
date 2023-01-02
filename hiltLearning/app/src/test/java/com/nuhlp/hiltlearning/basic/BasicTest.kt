package com.nuhlp.hiltlearning.basic


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class BasicTest {

    @Test
    fun sameObject(){
        val injector = BasicInjector()
        val b = injector.createB()
        val a = injector.createA(b)

        assertEquals(a.b,b)

    }
}