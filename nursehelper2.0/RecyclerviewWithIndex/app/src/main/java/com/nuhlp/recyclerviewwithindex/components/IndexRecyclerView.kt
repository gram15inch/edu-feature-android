package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

const val TAG3  = "IndexRecycler"
class IndexRecyclerView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {
    private val liveIndex = LiveIndex(context,this)

    override fun onTouchEvent(e: MotionEvent?): Boolean {
       if(liveIndex.onTouchEvent(e))
           return true
        else
            return super.onTouchEvent(e)
    }

    override fun draw(c: Canvas?) {
        super.draw(c)
        liveIndex.onDraw(c)
    }
}