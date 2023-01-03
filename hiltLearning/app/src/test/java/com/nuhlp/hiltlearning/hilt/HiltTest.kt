package com.nuhlp.hiltlearning.hilt

import dagger.hilt.EntryPoint
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject


@EntryPoint
class HiltTest {
    @Inject lateinit var a :ClassA

    @Test
    fun sameObject(){
        Assertions.assertEquals("call hilt B2", a.executeA())
    }

}