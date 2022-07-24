package com.nuhlp.recyclerviewwithindex.ui.learning

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.recyclerviewwithindex.R

const val TAG2  = "MyRecycler"
class MyRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {
    var isIndex = false
    override fun draw(c: Canvas?)  {
        super.draw(c)

        val p = Paint().apply {
            color = resources.getColor(R.color.black)
            isAntiAlias = true
            textSize = 24f // 실제크기 : 13f
        }
        p.textSize = 24f
        c!!.drawText("1.2.3.4.5.6.7.8.9.10.11.12.13.14.15.16.17.18.19", 80f, 60f, p)
        var strV = ""
        for (i in 1..12)
            c!!.drawText("$i", 45f, 30f * i + 30f, p)
        // todo 터치구역 나누기
    }

    private fun printLog(str: Any) {
        Log.d(TAG2, "$str")
    }

    override fun onTouchEvent(event: MotionEvent?) : Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.y < 70f)
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
        return  super.onTouchEvent(event)
    }

}
