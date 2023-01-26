package com.nuhlp.hiltlearning.ui



import androidx.lifecycle.ViewModel
import com.nuhlp.hiltlearning.basic.ClassA
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyHiltViewModel @Inject constructor(val a : ClassA) : ViewModel() {

    fun executeViewModel(){
        a.executeA()
        Timber.tag("timber").d("call MyHiltViewModel")
    }
}