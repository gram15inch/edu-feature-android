package com.nuhlp.databinding.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var text1 =0
    val liveText =MutableLiveData("")
    fun countUP(){
        text1++
        liveText.value = "$text1 single"
        Log.d("customView","MainViewModel call!!")
    }
    fun doubleCountUP():Boolean{
        text1+=2
        liveText.value = "$text1 double"
        Log.d("customView","MainViewModel call!!")
        return true
    }


}