package com.nuhlp.databinding.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nuhlp.databinding.R
import com.nuhlp.databinding.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    lateinit var binding : MainFragmentBinding
    companion object {
        fun newInstance() = MainFragment()
    }
    var data1 = "short/long"
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.fragment = this
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    fun onClickEvent() {
        this.viewModel.countUP()
    }
}