package com.nuhlp.customview.dataBinding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class DataBindingViewModel : ViewModel() {
   val flow1 = MutableSharedFlow<String>()

    fun flow1Call() {
        viewModelScope.let {
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("test","flow1Call!!")
                flow1.emit("call!") }
        }
    }
}