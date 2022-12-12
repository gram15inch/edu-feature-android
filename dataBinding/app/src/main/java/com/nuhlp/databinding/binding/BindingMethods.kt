package com.nuhlp.databinding.binding


import android.widget.TextView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.nuhlp.databinding.view.CustomView
@BindingMethods(
    BindingMethod(
        type = CustomView::class,
        attribute = "onCustomEventInMethods",
        method = "setOnCustomLongEventListener"),
    BindingMethod(
        type = TextView::class,
        attribute = "onCustomLongEventInMethods",
        method = "setOnLongClickListener")
)
class MyBindingMethods
