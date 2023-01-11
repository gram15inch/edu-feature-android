package com.learning.threadgame.model

import android.graphics.*
import timber.log.Timber

class MovingCar {
    constructor(id:Int,x:Int,y:Int,size:Int,color: Int){
        this.id = id
        this.x = x
        this.y = y
        this.width = size
        this.height = (size * 1.8).toInt()
        this.color = color
    }

    private var id: Int
    var direction = 1
    var step = 2
    var xy = true
    var x =0
    var y =0
    var width = 0
    var height = 0
    var paint =Paint()
    var boundary = Rect()
    var rectF = RectF()
    var color :Int
    fun draw(c: Canvas){
        paint.color = color
        if(xy)
            x += step* direction
        else
            y+= step * direction

        Timber.tag("cars").d("$id : $x")
        drawRect(c,x,y)
    }

    fun turn() {
        direction = if(direction == 1) -1 else 1
    }
    fun isCollision(xPos:Int,yPos:Int):Boolean{

       return (x<= xPos && xPos < x+width && y<= yPos && yPos < y+height ).apply { if(this) xy = !xy }
    }
    private fun drawRect(c :Canvas, px:Int,py:Int){
        rectF.left = px.toFloat()
        rectF.top = py.toFloat()
        rectF.right = px.toFloat() + width
        rectF.bottom = py.toFloat() + height
        c.drawRoundRect(rectF,0f,0f,paint)

    }
}