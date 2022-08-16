package com.nuhlp.customview.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.nuhlp.customview.R
import com.nuhlp.customview.databinding.ProfileItemBinding

class InflateXml @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    init {
        //val binding= ProfileItemBinding.inflate(LayoutInflater.from(context),this,true)
        val view= LayoutInflater.from(context).inflate(R.layout.profile_item, this, false)
        addView(view)
      /* binding.itemName.text="dynamic name"
       binding.itemContents.text="dynamic contents"*/



    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }


}