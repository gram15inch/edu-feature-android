package com.nuhlp.recyclerviewwithindex.ui.learning

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import com.nuhlp.recyclerviewwithindex.components.IndexRecyclerView

private fun LiveIndex_onDraw(){

    /*
    ** 인덱스 어답터 구분 배경색주입 **
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
*/
}
private fun IndexRecyclerView_init(){
    /*
    ** xml 에서 받은 값 뷰에 초기화
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
}