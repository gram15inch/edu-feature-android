package com.nuhlp.recyclerviewwithindex.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import com.nuhlp.recyclerviewwithindex.R


class MyCustomView : View{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    var pX = 45f
    var pY = 100f
    var pT = "position Text"

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val paint = Paint()
    // 크레파스의 색 정하기
        paint.setColor(Color.RED)

        // 도화지에 좌표로 표시하기
        val rect = RectF()
        rect[160f, 140f, 360f] = 640f

        canvas?.apply {
            val indexPaint = Paint().apply {
                color = resources.getColor(R.color.black)
                isAntiAlias = true
                textSize = 25F
            }
            var xPosition = 0f
            for( c in 1..30){
                if(c<=10)
                    xPosition += 25
                else
                    xPosition += 40
                drawText("$c", xPosition, pY ,indexPaint)
            }

            indexPaint.textSize = 45f
            //drawText(pT, 500f,500f,indexPaint)
            drawText(pT, pX,pY,indexPaint)
        }


    }

    fun setXY(x:Float,y:Float){
        pX = x
        pY = y
        invalidate()
    }
    private fun onRefresh() {
        invalidate()
        requestLayout()
    }

    fun setPt(str:String){
        pT = str
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action){
            MotionEvent.ACTION_MOVE->{
                setPt("${event.rawX} / ${event.rawY}")
                setXY(event.x,event.y)
                Log.d("tst","move on")
                false // move 는 반드시 false 반환
            }
            else -> true
        }
    }

}