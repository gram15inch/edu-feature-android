package com.nuhlp.recyclerviewwithindex.components

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
    var pY = 120f
    var pT = "position Text"

    var unit  = MutableLiveData<Int>()
    var lastElement = 0
    val pickIc = resources.getDrawable(R.drawable.ic_rpic, null)

    var indexSize = 25f
    var indexSize2 = indexSize + 10f
    var marginLeft = 15f
    private lateinit var itemList : List<Int>

    private fun updateItem(ver:Int) {
        val list = mutableListOf<Int>()
        when(ver){
            1->{
                for(i in 1..30)
                    if(i%2 == 0)
                        list.add(i)
            }
            2->{
                for(i in 1..30)
                    if(i%3 == 0)
                        list.add(i)
            }
            else->{
                for(i in 1..30)
                        list.add(i)
            }

        }
        itemList = list
    }

    private val colors = Array(50) {false}
    private val element_x_list = Array(50) {0f}

    init {
        updateItem(1)
    }

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
                textSize = 25f
            }

            var xPosition = marginLeft
            itemList.forEachIndexed(){i,c->
                xPosition +=
                    if(c < 11)
                        indexSize
                    else
                        indexSize2
                element_x_list[i]= xPosition
            }

            itemList.forEachIndexed() {i,c->

                // ** 마커 **
                if(colors[i])
                {   indexPaint.color = resources.getColor(R.color.purple_200)
                    //rect.set(c*25f + 12.5f,20f,c*25f+25+12.5f,40f) //위치
                    pickIc.setBounds(i*25 ,0,i*25+10+85,100) // 위치
                    pickIc.draw(canvas)

                    val tp = Paint()
                    tp.color = resources.getColor(R.color.white)

                    tp.textSize = 45f
                    drawText("$c", i*25f + 35f, 60f ,tp) // 글자

                }
                else
                    indexPaint.color = resources.getColor(R.color.black)

                // todo 1의자리 10의자리 별 위치 설정
                // ** index **
                val x = element_x_list[i] // index별 x위치
                val y = pY
                drawText("$c", x, y ,indexPaint)


            }


        }


    }

    private fun onRefresh() {
        invalidate()
        requestLayout()
    }

    fun getElement(x: Float) :Int {
        val toNineWidth = 25
        val toHundredWidth = toNineWidth + 15

        when{
            (x<0)->{ 0}
            (x>=0 && x<=toNineWidth*9)->{x.toInt()/toNineWidth }
           (x>toNineWidth*9) -> {x.toInt()/toHundredWidth }
           else -> 30
        }

        return 0
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action){
            MotionEvent.ACTION_MOVE->{
               /* val pxText = "${event.rawX.toInt()} / ${event.rawY.toInt()} \n ${event.x.toInt()} / ${event.y.toInt()}"
                setPt(pxText)*/

                colors?.set(lastElement,false)
                val curElement = getElement(event.x)

                colors?.set(curElement,true)
                unit.value = curElement
                lastElement= curElement

                invalidate()
                false // move 는 반드시 false 반환
            }
            else -> {
                colors?.set(lastElement,false)
                invalidate()
                true}
        }
    }

}