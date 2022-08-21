package com.nuhlp.customview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.nuhlp.customview.R

class Cavas1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
   private var str = "Cavas1"
   private var xPos = 50f
        set(value)  {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        val indexPaint = Paint().apply {
            color = context.resources.getColor(R.color.black)
            isAntiAlias = true
            textSize = 24f // 실제크기 : 13f
        }


        canvas?.also {
            if(xPos <100f)
            it.drawText(str, xPos, 50f ,indexPaint)
            if(xPos >=100f)
                it.drawText(str, xPos, 70f ,indexPaint)

        }

        Log.d("Canvas1","onDraw()")
    }
    fun reFlesh(str:String){
        this.str = str
        invalidate()
    }
}
