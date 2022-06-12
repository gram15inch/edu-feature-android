package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.nuhlp.recyclerviewwithindex.R


class MyCustomView(context: Context, attrs: AttributeSet) : View(context,attrs){

    var pX = 1f
    var pY = 1f


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val paint = Paint()
    // 크레파스의 색 정하기
        paint.setColor(Color.RED);

        // 도화지에 좌표로 표시하기
        val rect = RectF()
        rect[160f, 140f, 360f] = 640f



        canvas?.apply {
            val indexPaint = Paint().apply {
                color = resources.getColor(R.color.black)
                isAntiAlias =true
                textSize = 45F
            }
            for( c in 1..10){
                drawText("INDEX", pX, pY * c,indexPaint)}
        }




    }

    fun setXY(x:Float,y:Float){
        pX = x
        pY = y
    }
    private fun onRefresh() {
        invalidate()
        requestLayout()
    }


}