package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.recyclerviewwithindex.R

const val TAG2  = "MyRecycler"
class MyRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {
    override fun draw(c: Canvas?) {
        super.draw(c)

        val p = Paint().apply {
            color = resources.getColor(R.color.black)
            isAntiAlias = true
            textSize = 24f // 실제크기 : 13f
        }
        p.textSize=24f
        c!!.drawText("123456789",0f,100f,p)

    }
    private fun printLog(str:Any){
        Log.d(TAG2,"$str")
    }
}