package com.nuhlp.recyclerviewwithindex.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.nuhlp.recyclerviewwithindex.R
const val TAG ="indexComponent"

class MyCustomView : View{
    // 1. 코드에서 View 객체를 생성할 때 주로 호출하는 생성자
    constructor(context: Context) : super(context)
    // 2. 레이아웃xml에 등록한 View가 안드로이드에 의해 Inflate될 때 호출되는 생성자
    // 매개변수 AttributeSet 객체를 통해 attr.xml 에 정의한 커스텀 속성을 사용할 수 있다.
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    { xmlInit(context,attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    { xmlInit(context,attrs) }

    var pX = 45f
    var pY = 120f

    var unit  = MutableLiveData<Int>()
    val pickIc = resources.getDrawable(R.drawable.ic_rpiccut, null)

    var lastElement = 0
    var elementGapH = 10f
    var elementGapV = 10f
    var elementWidth = 13f
    var elementWidth2 = 25f
    var elementHeight = 19f
    var marginLeft = 15f
    var pickerIconWidth = 60
    var pickerIconHeight = 70
    var pickerIndexWidth = 24f
    var pickerIndexWidth2 = 48f
    var isHorizontal = true
    private var itemList = listOf<Int>()

    private lateinit var colors :Array<Boolean>
    private lateinit var pos_x_element :Array<Float>
    private lateinit var pos_x_picker_index :Array<Float>
    private lateinit var pos_x_picker_icon :Array<Float>


    private fun arrayInit(){
        colors = Array(50) {false}
        pos_x_element = Array(50) {-1f}
        pos_x_picker_index = Array(50) {-1f}
        pos_x_picker_icon = Array(50) {-1f}
    }
    private fun xmlInit(context:Context, attrs: AttributeSet?){
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView, 0, 0)
            if (typedArray != null) {
                try {
                    isHorizontal = typedArray.getBoolean(R.styleable.MyCustomView_isHorizontal,isHorizontal)
                } finally {
                    typedArray.recycle()
                }
            }
        }
    }

    fun updateItem(list: List<Int>) {
        itemList = list

        arrayInit()
        invalidate()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if(isHorizontal)
            drawHorizontal(canvas)
        else
            drawVertical(canvas)

    }

    private fun drawVertical(canvas: Canvas?) =canvas?.apply {
        val indexPaint = Paint().apply {
            color = resources.getColor(R.color.black)
            isAntiAlias = true
            textSize = 24f // 실제크기 : 13f
        }

        var yPosElement = 0f

        var yPosPickerIcon = 0f
        var yPosPickerIndex = 0f
        var lastElementForHeight = 0

        /* 1~99 까지 커버 */
        itemList.forEachIndexed(){i,c->
            yPosElement += elementHeight + elementGapH
            yPosPickerIcon = yPosElement - pickerIconWidth / 2 + elementWidth2 / 2
            yPosPickerIndex = yPosPickerIcon + (pickerIconWidth - pickerIndexWidth2) / 2

            pos_x_element[i] = yPosElement
            pos_x_picker_icon[i] = yPosPickerIcon
            pos_x_picker_index[i] = yPosPickerIndex
            lastElementForHeight = c
        }
        // *** 마커 ***
        itemList.forEachIndexed() {i,c->

            // ** icon **
            if(colors[i])
            {   indexPaint.color = resources.getColor(R.color.purple_200)
                pickIc.setBounds(0 ,pos_x_picker_icon[i].toInt(),pickerIconWidth, pos_x_picker_icon[i].toInt()+pickerIconHeight) // 위치
                pickIc.draw(canvas)

                val tp = Paint()
                tp.color = resources.getColor(R.color.white)
                tp.textSize = 45f

                drawText("$c", 20f, pos_x_picker_index[i] ,tp) // 글자
            }
            else indexPaint.color = resources.getColor(R.color.black)

            // ** index **
            val x = 0f // index별 x위치
            val y = pos_x_picker_index[i]
            drawText("$c", x, y ,indexPaint)
        }
    }

    private fun drawHorizontal(canvas: Canvas?)=canvas?.apply {
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
            //printLog("$i: $xPosElement")
            pos_x_element[i] = xPosElement
            pos_x_picker_icon[i] = xPosPickerIcon
            pos_x_picker_index[i] = xPosPickerIndex
            lastElementForWidth = c
        }

        // *** 마커 ***
        itemList.forEachIndexed() {i,c->

            // ** icon **
            if(colors[i])
            {   indexPaint.color = resources.getColor(R.color.purple_200)
                pickIc.setBounds(pos_x_picker_icon[i].toInt() ,0,pos_x_picker_icon[i].toInt()+pickerIconWidth, pickerIconHeight) // 위치
                pickIc.draw(canvas)

                val tp = Paint()
                tp.color = resources.getColor(R.color.white)
                tp.textSize = 45f

                drawText("$c", pos_x_picker_index[i], 50f ,tp) // 글자
            }
            else indexPaint.color = resources.getColor(R.color.black)

            // ** index **
            val x = pos_x_element[i] // index별 x위치
            val y = pY
            drawText("$c", x, 80f ,indexPaint)
        }

    }

    private fun onRefresh() {
        invalidate()
        requestLayout()
    }

    fun getElement(x: Float,y:Float) :Int {
        if(isHorizontal) {
            val a= pos_x_element.filter { it > 0 }.indexOfLast { it < x }
            if (a!=-1) return a
        }else{
            val a= pos_x_element.filter { it > 0 }.indexOfLast { it < y }
            if (a!=-1) return a
        }
      return lastElement
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action){
            MotionEvent.ACTION_MOVE->{
                colors?.set(lastElement,false)
                val curElement = getElement(event.x,event.y)

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

    fun orientaion(bool:Boolean) { isHorizontal = bool}

    private fun printLog(str:Any){
        Log.d(TAG,"$str")
    }
}