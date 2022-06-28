package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import com.nuhlp.recyclerviewwithindex.R

class LiveIndex {
    val TAG = "LiveIndex"

    val context :Context
    val recyclerView: IndexRecyclerView
    val pickIc : Drawable
    constructor(c: Context, rView: IndexRecyclerView)
    {
        this.context = c
        this.recyclerView = rView
        pickIc =  context.resources.getDrawable(R.drawable.ic_rpiccut, null)
        marginLeft = recyclerView.paddingLeft.toFloat()
    }

    var isIndex = false
    var pX = 45f
    var pY = 120f

    var unit  = MutableLiveData<Int>()


    var lastElement = 0
    var elementGapH = 10f
    var elementGapV = 10f
    var elementWidth = 13f
    var elementWidth2 = 25f
    var elementHeight = 19f
    var marginLeft :Float
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

    fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                val pTop = recyclerView.paddingTop.toFloat()
                val pLeft = recyclerView.paddingLeft.toFloat()
                if (e.y < pTop || e.x < pLeft ){
                    isIndex = true
                    return true
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if (isIndex) {
                    colors?.set(lastElement,false)
                    val curElement = getElement(e.x,e.y)

                    colors?.set(curElement,true)
                    unit.value = curElement
                    lastElement = curElement


                    return true // move 는 반드시 false 반환
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isIndex) {
                    isIndex = false

                    colors?.set(lastElement,false)
                    return true
                }
            }
        }

        return false
    }

    fun onDraw(canvas:Canvas?){
        val pTop = recyclerView.paddingTop.toFloat()
        val pLeft = recyclerView.paddingLeft.toFloat()
        val rWidth = recyclerView.width.toFloat()
        val rHeight = recyclerView.height.toFloat()
        val hr = RectF(0f,0f,rWidth,pTop)
        val vr = RectF(0f,0f,pLeft,rHeight)
        val p = Paint()
        p.color= Color.BLUE
        p.alpha = 30
        canvas!!.drawRect(hr,p)
        canvas!!.drawRect(vr,p)

        if(isHorizontal)
            drawHorizontal(canvas)
        else
            drawVertical(canvas)
    }
    fun updateItem(list: List<Int>) {
        itemList = list

        arrayInit()
        //invalidate()
    }



    private fun drawVertical(canvas: Canvas?) =canvas?.apply {
        val indexPaint = Paint().apply {
            color = context.resources.getColor(R.color.black)
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
            {   indexPaint.color =  context.resources.getColor(R.color.purple_200)
                pickIc.setBounds(0 ,pos_x_picker_icon[i].toInt(),pickerIconWidth, pos_x_picker_icon[i].toInt()+pickerIconHeight) // 위치
                pickIc.draw(canvas)

                val tp = Paint()
                tp.color = context.resources.getColor(R.color.white)
                tp.textSize = 45f

                drawText("$c", 20f, pos_x_picker_index[i] ,tp) // 글자
            }
            else indexPaint.color = context.resources.getColor(R.color.black)

            // ** index **
            val x = 0f // index별 x위치
            val y = pos_x_picker_index[i]
            drawText("$c", x, y ,indexPaint)
        }
    }

    private fun drawHorizontal(canvas: Canvas?)=canvas?.apply {
        val indexPaint = Paint().apply {
            color = context.resources.getColor(R.color.black)
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
            {   indexPaint.color = context.resources.getColor(R.color.purple_200)
                pickIc.setBounds(pos_x_picker_icon[i].toInt() ,0,pos_x_picker_icon[i].toInt()+pickerIconWidth, pickerIconHeight) // 위치
                pickIc.draw(canvas)

                val tp = Paint()
                tp.color = context.resources.getColor(R.color.white)
                tp.textSize = 45f

                drawText("$c", pos_x_picker_index[i], 50f ,tp) // 글자
            }
            else indexPaint.color = context.resources.getColor(R.color.black)

            // ** index **
            val x = pos_x_element[i] // index별 x위치
            val y = pY
            drawText("$c", x, 80f ,indexPaint)
        }

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

    private fun printLog(str: Any) {
        Log.d(TAG, "$str")
    }
}