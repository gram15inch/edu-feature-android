package com.learning.threadgame.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.view.Window
import com.learning.threadgame.thread.PotatoThread
import timber.log.Timber
import java.util.*

class Potato constructor(val bitmap: List<Bitmap>, x: Int, y: Int) {
    private var width = 150
    private var height = 150

    private val left = x
    private val right = x + width
    private val top = y
    private val bottom = y + height
    var potatoThread: PotatoThread? = null

    init {
        initPotato()
    }

    fun draw(canvas: Canvas) {
        if (potatoThread != null) {
            val dst = Rect(left, top, right, bottom)
            val grw = potatoThread!!.grown
            canvas.drawBitmap(bitmap[grw], null, dst, null)
        }

    }

    fun digPotato(): Boolean {
        if (potatoThread != null) {
            return potatoThread!!.grown == 5
        }
        return false
    }
    fun initPotato(){
        potatoThread = PotatoThread()
        potatoThread!!.start()
    }

    fun isClick(x: Float, y: Float): Boolean {
        return (x.toInt() in left until right) && (y.toInt() in top until bottom)
    }


}