package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent

class LiveIndex {
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
                if (e.y < 70f)
                    isIndex = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isIndex) {
                    return true // move 는 반드시 false 반환
                }
            }
            MotionEvent.ACTION_UP -> {
                isIndex = false
            }
        }

        return false
    }

    fun onDraw(canvas:Canvas?){
        // todo 터치 분리 / 색상 분리
    }
}