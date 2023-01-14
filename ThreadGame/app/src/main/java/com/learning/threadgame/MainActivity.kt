package com.learning.threadgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.learning.threadgame.model.BaseMap

class MainActivity : AppCompatActivity() {
    lateinit var map :BaseMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map = BaseMap(this)
        setContentView(map)
    }
}