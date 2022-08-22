package com.nuhlp.customview.dataBinding

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.nuhlp.customview.views.Cavas1
import com.nuhlp.customview.views.InflateConsMergeXml
import com.nuhlp.customview.views.InflateConsXml
import com.nuhlp.customview.views.InflateRelateXml
import com.nuhlp.customview.views.normal.NormalConsMerge


@BindingAdapter("bindViewModel")
fun bindView1(view: Cavas1, viewModel: DataBindingViewModel) {
        view.reFlesh("Cavas1")
}
@BindingAdapter("bindViewModel")
fun bindView2(view: InflateRelateXml, viewModel: DataBindingViewModel) {
        view.dataBinding.itemName.text = "InflateRelateXml"
        view.dataBinding.itemContents.text = "adapter dataBinding"
}
@BindingAdapter("bindViewModel","bindLifecycle")
fun bindView3(view: InflateConsXml, viewModel: DataBindingViewModel,lifecycle:LifecycleOwner) {
        viewModel.flow1.asLiveData().observe(lifecycle){
                Log.d("test","InflateConsXml observe!!")
                view.dataBinding.textName.text = "InflateConsXml $it"
        }
        view.dataBinding.textContents.text = "adapter dataBinding"
}
@BindingAdapter("bindViewModel","bindLifecycle")
fun bindView4(view: InflateConsMergeXml, viewModel: DataBindingViewModel,lifecycle:LifecycleOwner) {
        viewModel.flow1.asLiveData().observe(lifecycle){
                Log.d("test","InflateConsMergeXml observe!!")
                view.dataBinding.textName.text = "InflateConsMergeXml $it"
        }
        view.dataBinding.textContents.text = "adapter dataBinding"
}
@BindingAdapter("bindViewModel","bindLifecycle")
fun bindView5(view: NormalConsMerge, viewModel: DataBindingViewModel,lifecycle:LifecycleOwner) {
        view.viewBinding.textContents.text = "adapter dataBinding "
        viewModel.flow1.asLiveData().observe(lifecycle){
                Log.d("test","NormalConsMerge observe!!")
                view.viewBinding.textName.text = "NormalConsMerge $it"
        }
}

//todo 본앱에 적용하기