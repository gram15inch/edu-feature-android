package com.learning.threadgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.learning.threadgame.model.BaseMap
import com.learning.threadgame.model.MovingCar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var map :BaseMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map = BaseMap(this)

        setContentView(map)
    }
}