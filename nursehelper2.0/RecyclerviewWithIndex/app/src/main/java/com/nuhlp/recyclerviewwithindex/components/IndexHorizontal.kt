package com.nuhlp.recyclerviewwithindex.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nuhlp.recyclerviewwithindex.R
import com.nuhlp.recyclerviewwithindex.databinding.IndexHorizontalBinding


class IndexHorizontal(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var elements: List<TextView>
    val binding: IndexHorizontalBinding
    val cList: List<TextView>

    init {
        binding = IndexHorizontalBinding.inflate(LayoutInflater.from(context), this, true)
        cList = binding.root.children.toList() as List<TextView>

        context.theme.obtainStyledAttributes(attrs, R.styleable.index_horizontal, 0, 0)
            .apply {
                try {
                    val indexString = getString(R.styleable.index_horizontal_indexString) ?: ""
                    setElements(indexString)
                    //val typeArray = context.obtainStyledAttributes(attrs, R.styleable.index_horizontal)
                }
                finally { recycle() }
            }

        setListener()


    }
    private fun setListener() =binding.apply {

        // todo 호버시 색변경
        cList.map { it.setOnHoverListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_HOVER_ENTER->{ v.setBackgroundColor(resources.getColor(R.color.purple_200)); true}
                MotionEvent.ACTION_HOVER_EXIT->{v.setBackgroundColor(resources.getColor(R.color.purple_500)); true}
                else->{false}
            }
        } }
    }
    private fun onRefresh() {
        invalidate()
        requestLayout()
    }


    fun setElements(str: String) {
        val strList = str.split(" ")

        val strIt = strList.iterator()

        cList.map {
            if (strIt.hasNext()) {
                it.text = strIt.next()
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.INVISIBLE
            }
        }
        onRefresh()
    }




}