package com.nuhlp.customview.dataBinding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nuhlp.customview.R
import com.nuhlp.customview.binding.BaseDataBindingFragment
import com.nuhlp.customview.databinding.DataBindingFragmentBinding
import com.nuhlp.customview.nemal.NomalViewModel

class DataBindingFragment : BaseDataBindingFragment<DataBindingFragmentBinding>() {


    private lateinit var viewModel: DataBindingViewModel
    override val layoutResourceId: Int
        get() = R.layout.data_binding_fragment

    override fun onCreateViewAfterBinding() {
        viewModel = ViewModelProvider(this).get(DataBindingViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

    }

    override fun onResume() {
        super.onResume()
        viewModel.flow1Call()
    }
}