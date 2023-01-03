package com.nuhlp.hiltlearning.ui



import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyHiltViewModel @Inject constructor() : ViewModel() {

    fun executeViewModel(){
        Timber.tag("timber").d("call MyHiltViewModel")
    }
}