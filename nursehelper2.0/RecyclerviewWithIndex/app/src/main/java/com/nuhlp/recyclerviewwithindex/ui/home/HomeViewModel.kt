package com.nuhlp.recyclerviewwithindex.ui.home

import android.content.ClipData
import androidx.lifecycle.*
import com.nuhlp.recyclerviewwithindex.data.doc
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel(){
    val allDocs: MutableLiveData<List<doc>> = MutableLiveData()


    fun updateDocs(month:Int){
         allDocs.value = doc.getStub(month,2)
    }


}

class HomeViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // 뷰모델 클레스부분만 수정
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            // 만들 뷰모델 반환
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}