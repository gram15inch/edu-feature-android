package com.nuhlp.recyclerviewwithindex

import org.junit.Test

class colTest {

    @Test
    fun test1(){

        val af = Array(20) {0f}
        af.forEachIndexed(){ i: Int, f: Float ->
             af[i] = (i*2).toFloat()
        }
        var str = ""
        for(s in af)
            str += "$s  "
        println(str)
        val a = af.indexOfLast { it <5 }
        print("a: $a")
    }

}