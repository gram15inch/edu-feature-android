package com.nuhlp.hiltlearning.basic


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

data class Category(val num:Int,val str :String)
class BasicTest {

    @Test
    fun sameObject(){
        val injector = BasicInjector()
        val b = injector.createB()
        val a = injector.createA(b)

        assertEquals(a.b,b)

    }

    @Test
    fun sameDataClass(){
        assertEquals(true,Category(1,"10")==Category(1,"10"))
        assertEquals(true,Category(1,"10").equals(Category(1,"10")))
        assertEquals(false,Category(1,"10")==Category(1,"11"))
        assertEquals(false,Category(1,"10").equals(Category(1,"11")))
    }
}