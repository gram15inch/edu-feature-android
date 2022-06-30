package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.recyclerviewwithindex.R

class IndexRecyclerView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : RecyclerView(context, attrs) {
    val TAG  = "IndexRecycler"
    private val liveIndexH = LiveIndex(context,this)
    private val liveIndexV = LiveIndex(context,this)

    init{
        liveIndexV.isHorizontal = false
        liveIndexH.updateItem(createItem(30))
        liveIndexV.updateItem(createItem(12))
        background = context.resources.getDrawable(R.drawable.bg_rect_round_2)
    }

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
       if(liveIndexH.onTouchEvent(e)||liveIndexV.onTouchEvent(e)){
           invalidate()
           return true
       }
        else
            return super.onTouchEvent(e)
    }

    override fun draw(c: Canvas?) {
        super.draw(c)
        liveIndexH.onDraw(c)
        liveIndexV.onDraw(c)
        //todo 아이콘 제대로 수직에 표시
    }
    private fun printLog(str: Any) {
        Log.d(TAG, "$str")
    }

    fun createItem(i :Int):List<Int>{
        val list = mutableListOf<Int>()
        when(i){
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
            12->{
                for(i in 1..12)
                    list.add(i)
            }
            30->{
                for(i in 1..30)
                    list.add(i)
            }
            else->{
                for(i in 1..1)
                    list.add(i)
            }

        }
        return list
    }
}