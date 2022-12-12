package com.nuhlp.databinding.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    interface OnCustomEventListener{
        fun onEvent()
    }

    interface OnCustomLongEventListener{
        fun onEvent() :Boolean
    }

    private lateinit var eventListener : OnCustomEventListener
    lateinit var eventLongListener : OnCustomLongEventListener

    fun setOnCustomEventListener(listener: OnCustomEventListener) {
        eventListener=listener
        setOnClickListener { eventListener.onEvent() }
    }

    fun setOnCustomLongEventListener(listener: OnCustomLongEventListener) {
        eventLongListener=listener
        setOnLongClickListener { eventLongListener.onEvent() }
    }
}