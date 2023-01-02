package com.nuhlp.hiltlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


class ClassA(private val b: ClassB) {
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