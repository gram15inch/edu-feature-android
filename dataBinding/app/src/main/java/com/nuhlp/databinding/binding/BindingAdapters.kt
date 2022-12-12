package com.nuhlp.databinding.binding


import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.*
import androidx.lifecycle.MutableLiveData
import com.nuhlp.databinding.view.CustomView
import java.util.EventListener

object BindingAdapters {

    /**
     * View의 visibility를 변경
     * @param view 속성을 사용하는 view
     * @param isVisible visibility를 변경시키는 기준이 되는 값
     */

    @JvmStatic
    @BindingAdapter("etInOut")
    fun setET(view: TextView,liveData: MutableLiveData<String>) {
        if(liveData.value==view.text.toString()) //  get/set 무한루프 방지
            return
        //view.setText("${liveData.value}")
        view.text ="${liveData.value}"
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "etInOut", event = "textEvent")
    fun getET(view: TextView):String {
       return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("textEvent")
    fun textEventListener(view: EditText,listener: InverseBindingListener){
        view.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listener.onChange()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

    }

    @JvmStatic
    @BindingAdapter("onCustomEventInAdapter")
    fun onCustomEvent(view: CustomView, listener: CustomView.OnCustomEventListener) {
        view.setOnCustomEventListener(listener)
    }

}
