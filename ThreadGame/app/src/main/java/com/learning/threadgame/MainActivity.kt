package com.learning.threadgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.threadgame.view.BaseMap

class MainActivity : AppCompatActivity() {
    lateinit var map :BaseMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map = BaseMap(this)
        setContentView(map)
    }
}