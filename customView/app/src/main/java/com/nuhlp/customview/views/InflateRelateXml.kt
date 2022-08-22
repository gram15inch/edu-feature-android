package com.nuhlp.customview.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.nuhlp.customview.R
import com.nuhlp.customview.databinding.DataBindingRelateBinding


//todo 인플레이트 안보이는 문제 해결
class InflateRelateXml @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    //  lateinit var viewBinding : ItemProfileRelateBinding
    lateinit var dataBinding : DataBindingRelateBinding

    init{
        //viewBindingInit(context)
        dataBindingInit(context)
    }

    private fun dataBindingInit(context: Context){
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.data_binding_relate, this, true)
        dataBinding.itemContents.text = "class dataBinding"
    }
    private fun viewBindingInit(context: Context) {
        /* 원래코드
          viewBinding = ItemProfileRelateBinding.inflate(LayoutInflater.from(context),this,true)
          viewBinding.itemContents.text = "viewBinding"*/

    }




}