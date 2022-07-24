package com.nuhlp.recyclerviewwithindex.ui.learning

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
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
    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() =binding.apply {

        // todo 호버시 색변경
        cList.map { it.setOnTouchListener { v, event ->
            when(event.action){
              /*  MotionEvent.ACTION_DOWN->{v.setBackgroundColor(resources.getColor(R.color.purple_200)); true}
                MotionEvent.ACTION_UP->{v.setBackgroundColor(resources.getColor(R.color.white)); true}*
               */
                else->{false}
            }
        } }

        /*this.root.setOnTouchListener { v, event ->
            event.source
            true
        }*/
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