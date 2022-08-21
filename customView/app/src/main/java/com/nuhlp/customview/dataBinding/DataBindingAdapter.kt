package com.nuhlp.customview.dataBinding

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import com.nuhlp.customview.views.Cavas1
import com.nuhlp.customview.views.InflateConsMergeXml
import com.nuhlp.customview.views.InflateConsXml
import com.nuhlp.customview.views.InflateRelateXml


@BindingAdapter("bindViewModel")
fun bindView1(view: Cavas1, viewModel: DataBindingViewModel) {
        view.reFlesh("Cavas1")
}
@BindingAdapter("bindViewModel")
fun bindView2(view: InflateRelateXml, viewModel: DataBindingViewModel) {
        view.dataBinding.itemName.text = "InflateRelateXml"
        view.dataBinding.itemContents.text = "adapter dataBinding"
}
@BindingAdapter("bindViewModel")
fun bindView3(view: InflateConsXml, viewModel: DataBindingViewModel) {
        view.dataBinding.textName.text = "InflateConsXml"
        view.dataBinding.textContents.text = "adapter dataBinding"
}
@BindingAdapter("bindViewModel")
fun bindView4(view: InflateConsMergeXml, viewModel: DataBindingViewModel) {
        view.dataBinding.textName.text = "InflateConsMergeXml"
        view.dataBinding.textContents.text = "adapter dataBinding"
}