package com.learning.threadgame.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.view.Window
import timber.log.Timber
import java.util.*

class Potato constructor(val bitmap: List<Bitmap>, x: Int, y: Int) {
    private var width = 150
    private var height = 150
    private var state = 0
    private var speed = (1..9).random()
    private var isLive = true
    private var isThreadRun = true
    private val left = x
    private val right = x + width
    private val top = y
    private val bottom = y + height

    init {
        run()
    }

    fun draw(canvas: Canvas) {
        val dst = Rect(left, top, right, bottom)
        canvas.drawBitmap(bitmap[state], null, dst, null)
    }

    fun initPotato() {
        state = 0
        isLive = true
        speed = (1..9).random()
        run()
    }

    fun digPotato():Boolean{
        isThreadRun = false
        return state == 5
    }

    private fun run() {
        Thread() {
            Timber.tag("score").d("Thread ON ")
            while (isLive&&isThreadRun) {
                Thread.sleep((2000 - (speed * 100)).toLong())
                state++
                if (state >= 6)
                    isLive = false
            }
            Timber.tag("score").d("Thread OFF ")
        }.start()


    }

    fun isClick(x: Float, y: Float): Boolean {
        return (x.toInt() in left until right) && (y.toInt() in top until bottom)
    }
}