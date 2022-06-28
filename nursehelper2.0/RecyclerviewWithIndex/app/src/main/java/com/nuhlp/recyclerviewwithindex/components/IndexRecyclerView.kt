package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.recyclerviewwithindex.R

class IndexRecyclerView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : RecyclerView(context, attrs) {
    val TAG  = "IndexRecycler"
    private val liveIndex = LiveIndex(context,this)


    /*
    init {
        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs,
                    androidx.viewpager2.R.styleable.RecyclerView,
                    0,
                    0)
            if (typedArray != null) {
                try {
                    setIndexTextSize =
                        typedArray.getInt(R.styleable.IndexFastScrollRecyclerView_setIndexTextSize,
                            setIndexTextSize)
                    mIndexbarWidth =
                        typedArray.getFloat(R.styleable.IndexFastScrollRecyclerView_setIndexbarWidth,
                            mIndexbarWidth)
                } finally {
                    typedArray.recycle()
                }
            }
        }
    }
   */
    override fun onTouchEvent(e: MotionEvent?): Boolean {
       if(liveIndex.onTouchEvent(e)){
           return true
       }
        else
            return super.onTouchEvent(e)
    }

    override fun draw(c: Canvas?) {
        super.draw(c)
        liveIndex.onDraw(c)
    }
    private fun printLog(str: Any) {
        Log.d(TAG, "$str")
    }
}