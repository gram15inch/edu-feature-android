package com.learning.threadgame.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import java.util.*

class Potato constructor(val bitmap: List<Bitmap>,val x :Int,val y :Int) {
    var width = 150
    var height = 150
    var state = (0..6).random()

    init {
        Thread(){
            while(state<6){
                Thread.sleep(1000)
                state = (state +1)%7
            }
        }.start()
    }
    fun draw(canvas: Canvas){
        val dst = Rect(x, y, x+width , y+height )
        canvas.drawBitmap(bitmap[state], null, dst, null)
    }

    fun initPotato(){
        state= 0
    }
}