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

    var elementSize = 12f
    var elementSize2 = elementSize + 12f
    var marginLeft = 15f
    var pickerWidth = 90
    var pickerHeight = 100
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
    private val pos_x_element = Array(50) {-1f}
    private val pos_x_picker_index = Array(50) {-1f}
    private val pos_x_picker_icon = Array(50) {-1f}


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
                textSize = 24f
            }

            var xPosElement = marginLeft

            var xPosPickerIcon = 0f
            var xPosPickerIndex = 0f

            itemList.forEachIndexed(){i,c->

                if (c == 10) {
                    xPosElement += elementSize+10f
                    xPosPickerIcon = xPosElement - (pickerHeight - elementSize) / 2 + 5f
                    xPosPickerIndex = xPosElement - (pickerHeight - elementSize) / 2 + 5f
                } else if (c < 11) {
                    xPosElement += elementSize+10f
                    xPosPickerIcon = xPosElement - (pickerHeight - elementSize) / 2
                    xPosPickerIndex = xPosElement - (pickerHeight - elementSize) / 2
                } else {
                    xPosElement += elementSize2+10f
                    xPosPickerIcon = xPosElement - (pickerHeight - elementSize2) / 2 + 5f
                    xPosPickerIndex = xPosElement - (pickerHeight - elementSize2) / 2 + 5f
                }

                pos_x_element[i] = xPosElement
                pos_x_picker_icon[i] = xPosPickerIcon
                pos_x_picker_index[i] = xPosPickerIndex


            }

            itemList.forEachIndexed() {i,c->

                // ** 마커 **
                if(colors[i])
                {   indexPaint.color = resources.getColor(R.color.purple_200)
                    pickIc.setBounds(pos_x_picker_icon[i].toInt() ,0,pos_x_picker_icon[i].toInt()+pickerWidth, pickerHeight) // 위치
                    pickIc.draw(canvas)

                    val tp = Paint()
                    tp.color = resources.getColor(R.color.white)

                    tp.textSize = 45f
                    drawText("$c", i*25f + 35f, 60f ,tp) // 글자
                }
                else
                    indexPaint.color = resources.getColor(R.color.black)
                //todo text 길이 체크
                
                // ** index **
                val x = pos_x_element[i] // index별 x위치
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

        pos_x_element.filter { it > 0 }.forEachIndexed(){ i, e->
            if(e>x)
               return i
        }

      return lastElement
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