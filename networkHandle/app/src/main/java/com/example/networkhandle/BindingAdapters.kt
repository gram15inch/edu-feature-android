package com.example.networkhandle

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.networkhandle.data.model.signup.RemoteSignUp
import com.example.networkhandle.data.model.user.RemoteUser
import timber.log.Timber


object BindingAdapters {
    @JvmStatic
    @BindingAdapter("remoteUser")
    fun bindRemoteUser(view: TextView, list: List<RemoteUser>?) {
       view.text = list?.toString()?:"emptyList"
    }
    @JvmStatic
    @BindingAdapter("remoteSignUp")
    fun bindRemoteSignUp(view: TextView, value: RemoteSignUp?) {
       view.text = value?.toString()?:"empty"
    }
    @JvmStatic
    @BindingAdapter("idxText")
    fun bindIdxText(view: TextView, value: String?) {
       view.text = value?:""
    }

}