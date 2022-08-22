package com.nuhlp.customview.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nuhlp.customview.R
import com.nuhlp.customview.databinding.DataBindingConsBinding


class InflateConsXml @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

  //  lateinit var viewBinding : ItemProfileConsBinding

    lateinit var dataBinding : DataBindingConsBinding

    init{

    dataBindingInit(context)
    //viewBindingInit(context)
    }

    private fun dataBindingInit(context: Context){
       dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.data_binding_cons, this, true)
    }
    private fun viewBindingInit(context: Context) {
      /* 원래코드
        viewBinding = ItemProfileConsBinding.inflate(LayoutInflater.from(context),this,true)
        viewBinding.textContents.text = "viewBinding"*/

        /*val view = LayoutInflater.from(context).inflate(R.layout.item_profile,this,false)
        addView(view)*/

        /*val binding = ItemProfileBinding.inflate(LayoutInflater.from(context),this,true)
        binding.itemName.text = "ihihihihih"*/

        Log.d("InflateXml","call init!")

    }

}