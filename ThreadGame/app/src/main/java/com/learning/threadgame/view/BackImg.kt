package com.learning.threadgame.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect



class BackImg constructor(val bitmap: Bitmap) {

    fun draw(canvas: Canvas){
       val dst = Rect(0, 0, 1080 , 1920 )
        canvas.drawColor(Color.parseColor("#B0905D"))
        canvas.drawBitmap(bitmap, null, dst, null)
    }
}