package com.learning.threadgame.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class UiStatus {
    var score =0
    var life = 3
    var textPaint = Paint().also {
        it.color = Color.WHITE
        it.textSize = 80f
    }
    fun draw(canvas: Canvas){
        canvas.drawText("SCORE: $score",20f,90f, textPaint)
        var heart = ""
        repeat(life){
           heart += "❤"
        }
        canvas.drawText("LIFE: $heart",580f,90f, textPaint)
    }
}