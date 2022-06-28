package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent

class LiveIndex {
    val TAG = "LiveIndex"
    var isIndex = false
    val context :Context
    val recyclerView: IndexRecyclerView

    constructor(c: Context, rView: IndexRecyclerView)
    {
        this.context = c
        this.recyclerView = rView
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
                    return true // move 는 반드시 false 반환
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isIndex) {
                    isIndex = false
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
    }
    private fun printLog(str: Any) {
        Log.d(TAG, "$str")
    }
}