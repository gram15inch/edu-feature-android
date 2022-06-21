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
    val pickIc = resources.getDrawable(R.drawable.ic_rpiccut, null)
    var elementGapH = 10f
    var elementWidth = 13f
    var elementWidth2 = 26f
    var marginLeft = 15f
    var pickerIconWidth = 60
    var pickerIconHeight = 90
    var pickerIndexWidth = 24f
    var pickerIndexWidth2 = 48f

    private lateinit var itemList : List<Int>

    fun updateItem(ver:Int) {
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
        invalidate()
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
                textSize = 24f // 실제크기 : 13f
            }


            var xPosElement = marginLeft

            var xPosPickerIcon = 0f
            var xPosPickerIndex = 0f

            var lastElementForWidth = 0
            /* 1~99 까지 커버 */
            itemList.forEachIndexed(){i,c->




               // if (c == 10) {
                if(lastElementForWidth <= 9 && c>10){
                    xPosElement += elementWidth + elementGapH
                    xPosPickerIcon = xPosElement - pickerIconWidth / 2 + elementWidth2 / 2
                    xPosPickerIndex = xPosPickerIcon + (pickerIconWidth - pickerIndexWidth2) / 2
                } else if (c <= 10) {
                    xPosElement += elementWidth + elementGapH
                    xPosPickerIcon = xPosElement - pickerIconWidth / 2 + elementWidth / 2
                    xPosPickerIndex = xPosPickerIcon + (pickerIconWidth - pickerIndexWidth) / 2
                } else {
                    xPosElement += elementWidth2 + elementGapH
                    xPosPickerIcon = xPosElement - pickerIconWidth / 2 + elementWidth2 / 2
                    xPosPickerIndex = xPosPickerIcon + (pickerIconWidth - pickerIndexWidth2) / 2
                }
                pos_x_element[i] = xPosElement
                pos_x_picker_icon[i] = xPosPickerIcon
                pos_x_picker_index[i] = xPosPickerIndex

                lastElementForWidth = c

            }

            itemList.forEachIndexed() {i,c->

                // ** 마커 **
                if(colors[i])
                {   indexPaint.color = resources.getColor(R.color.purple_200)
                    pickIc.setBounds(pos_x_picker_icon[i].toInt() ,0,pos_x_picker_icon[i].toInt()+pickerIconWidth, pickerIconHeight) // 위치
                    pickIc.draw(canvas)

                    val tp = Paint()
                    tp.color = resources.getColor(R.color.white)
                    tp.textSize = 45f
                    drawText("$c", pos_x_picker_index[i], 50f ,tp) // 글자
                }
                else
                    indexPaint.color = resources.getColor(R.color.black)
                
                // ** index **
                val x = pos_x_element[i] // index별 x위치
                val y = pY
                drawText("$c", x, y ,indexPaint)
                 val str = indexPaint.measureText("$c").toString()
                //Log.d("indexComponent",str)
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
                setPt(pxText) */

                colors?.set(lastElement,false)
                val curElement = getElement(event.x)

                colors?.set(curElement,true)
                unit.value = curElement
                lastElement = curElement

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