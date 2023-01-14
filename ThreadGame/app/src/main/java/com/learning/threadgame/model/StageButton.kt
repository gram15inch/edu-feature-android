package com.learning.threadgame.model

import android.graphics.*
import timber.log.Timber

class StageButton constructor(val text: String, val x: Int, val y: Int) {
    private var width: Int
    private var height: Int

    private val left: Int
    private val right: Int
    private val top: Int
    private val bottom: Int
    private val rect = Rect()
    private var textPaintStep = Paint().also {
        it.color = Color.WHITE
        it.textSize = 170f
        it.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    init {
        textPaintStep.getTextBounds(text, 0, text.length, rect)
        width = rect.width()
        height = rect.height()
        left = x
        right = x + width
        top = y - height
        bottom = y
    }

    fun draw(canvas: Canvas) {
        canvas.drawText(text, x.toFloat(), y.toFloat(), textPaintStep)
    }

    fun isClick(x: Float, y: Float): Boolean {
        val px = x.toInt()
        val py = y.toInt()
        Timber.tag("score").d("(x,y): (${this.x},${this.y}),($px,$py) ")
        return (x.toInt() in left until right) && (y.toInt() in top until bottom)
    }
}