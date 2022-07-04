package com.nuhlp.databinding


import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.w3c.dom.Text

object BindingAdapters {

    /**
     * View의 visibility를 변경
     * @param view 속성을 사용하는 view
     * @param isVisible visibility를 변경시키는 기준이 되는 값
     */
    @JvmStatic
    @BindingAdapter("text1")
    fun setText12(view: View, text: Any) {
        (view as TextView).text  ="$text"
    }
}