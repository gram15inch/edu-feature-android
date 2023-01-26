package com.example.networkhandle

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.networkhandle.data.model.RemoteUser
import timber.log.Timber


object BindingAdapters {
    @JvmStatic
    @BindingAdapter("remoteUser")
    fun bindRemoteUser(view: TextView, list: List<RemoteUser>?) {
        Timber.tag("response error").d("b size: ${list?.size?:0}")
       view.text = list?.toString()?:"emptyList"
    }

}