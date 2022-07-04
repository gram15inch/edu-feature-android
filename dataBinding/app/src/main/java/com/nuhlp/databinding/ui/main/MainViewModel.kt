package com.nuhlp.databinding.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val text1 =MutableLiveData(0)

    fun countUP(){
        text1.value =  text1.value!!+ 1
    }
}