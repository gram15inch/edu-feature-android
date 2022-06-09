package com.nuhlp.recyclerviewwithindex

import android.util.Log
import android.widget.TextView
import androidx.core.view.children
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val clist : List<MyTextView>
    val str :String
    init {
        val list = mutableListOf<MyTextView>()
        repeat(30){
            list.add(MyTextView())
        }
        clist = list.toList()
        str ="1 2 3 4 5 6 7 8 9 10"
    }


    private fun setElements1() {
        // todo 커스텀 뷰에 글자넣기
        val a =  str.split(" ")
        val ai= a.iterator()
        for (c in clist){
            if(ai.hasNext()){
                c.text= ai.next()
            }else
                c.text="*"
        }
    }
    fun setElements2() {
        val a =  str.split(" ")
        val ai= a.iterator()
        for (c in clist){
            if(ai.hasNext()){
                c.text= ai.next()
            }else
                c.text="*"
        }

        clist.map {  }
    }

    @Test
    fun startTest(){
        codeTime { setElements1() }
        codeTime { setElements2() }
    }

    @OptIn(ExperimentalTime::class)
    fun codeTime(inCode : ()->Unit ){
        val measuredTime = measureTimedValue {
           inCode.invoke()
        }
        println("result : ${measuredTime.value}, measured time : ${measuredTime.duration}")
    }


    class MyTextView{
        var text ="0"
    }
}


