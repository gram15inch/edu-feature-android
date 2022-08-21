package com.nuhlp.customview.views



import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.nuhlp.customview.R
import com.nuhlp.customview.databinding.DataBindingConsMergeBinding



class InflateConsMergeXml @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    //  lateinit var viewBinding : ItemProfileRelateBinding
    lateinit var dataBinding : DataBindingConsMergeBinding

    init{

        //viewBindingInit(context)
        dataBindingInit(context)
    }

    private fun dataBindingInit(context: Context){
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.data_binding_cons_merge, this, true)
    }
    private fun viewBindingInit(context: Context) {
        /* 원래코드
          viewBinding = ItemProfileRelateBinding.inflate(LayoutInflater.from(context),this,true)
          viewBinding.itemContents.text = "viewBinding"*/

    }


}