package com.nuhlp.customview.views.normal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.nuhlp.customview.R
import com.nuhlp.customview.databinding.ItemProfileConsBinding
import com.nuhlp.customview.databinding.NomalConsMergeBinding

class NormalConsMerge @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val viewBinding: NomalConsMergeBinding
    init {
        viewBinding = NomalConsMergeBinding.inflate(LayoutInflater.from(context),this)
        viewBinding.textContents.text = "binding before"
    }
}