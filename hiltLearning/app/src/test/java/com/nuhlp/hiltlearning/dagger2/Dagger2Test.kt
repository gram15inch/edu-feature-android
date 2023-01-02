package com.nuhlp.hiltlearning.dagger2

import dagger.internal.DaggerGenerated
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Dagger2Test {

    @Test
    fun sameObject(){

        val component = DaggerBindComponent.create()
        
        Assertions.assertEquals(1, 1)
    }

}