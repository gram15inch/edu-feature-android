package com.nuhlp.recyclerviewwithindex.components

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
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

    var unit  = MutableLiveData<Int>()
    var lastElement = 0
    val rectD = resources.getDrawable(R.drawable.rect1, null)
    private val colors = Array(50) {false}

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val paint = Paint()
    // 크레파스의 색 정하기
        paint.setColor(Color.RED)

        // 도화지에 좌표로 표시하기
        val rect = RectF()

        canvas?.apply {
            val indexPaint = Paint().apply {
                color = resources.getColor(R.color.black)
                isAntiAlias = true
                textSize = 25F
            }
            var xPosition = 0f
            for( c in 0..9){
                if(c<=9)
                    xPosition += 25
                else
                    xPosition += 40



                if(colors[c])
                {   indexPaint.color = resources.getColor(R.color.purple_200)
                    rect.set(c*25f + 12.5f,20f,c*25f+25+12.5f,40f)
                    //drawRect(rect,indexPaint)
                    rectD.setBounds(c*25 ,0,c*25+10+65,65)
                    rectD.draw(canvas)
                    //color = resources.getColor(R.color.white)
                    //drawText("$c", 100f, 100f ,indexPaint)
                    // todo 유닛 그래픽에 글자넣기
                }
                else
                    indexPaint.color = resources.getColor(R.color.black)

                drawText("$c", xPosition, pY ,indexPaint)
            }


        }


    }

    private fun onRefresh() {
        invalidate()
        requestLayout()
    }


    fun getElement(x: Float) :Int {
        val toNineWidth = 25
        val toHundredWidth = 45

       return when{
            (x<0)->{ 0}
            (x>=0 && x<=toNineWidth*9)->{x.toInt()/toNineWidth }
           (x>toNineWidth*9) -> 9
           else -> 9
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action){
            MotionEvent.ACTION_MOVE->{
               /* val pxText = "${event.rawX.toInt()} / ${event.rawY.toInt()} \n ${event.x.toInt()} / ${event.y.toInt()}"
                setPt(pxText)*/

                colors?.set(lastElement,false)
                val curElement = getElement(event.x)
                // 거 준구형 장난이 너무 심한거 아니오!!
                colors?.set(curElement,true)
                unit.value = curElement
                lastElement= curElement

                invalidate()
                false // move 는 반드시 false 반환
            }
            else -> true
        }
    }

}