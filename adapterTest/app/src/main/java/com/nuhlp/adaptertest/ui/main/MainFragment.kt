package com.nuhlp.adaptertest.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuhlp.adaptertest.MyAdapter
import com.nuhlp.adaptertest.R
import com.nuhlp.adaptertest.base.BaseDataBindingFragment
import com.nuhlp.adaptertest.databinding.MainFragmentBinding

class MainFragment :BaseDataBindingFragment<MainFragmentBinding>() {
    override var layoutResourceId = R.layout.main_fragment
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }


    override fun onCreateViewAfterBinding() {
        binding.viewModel = viewModel
        binding.rcycleView.apply{
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
            val a = MyAdapter()
            a.submitList( listOf(MyAdapter.Schedule(1),MyAdapter.Schedule(2)))
            adapter = a
        }

    }


}