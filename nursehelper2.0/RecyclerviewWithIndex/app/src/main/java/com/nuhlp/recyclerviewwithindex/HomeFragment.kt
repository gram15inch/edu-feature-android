package com.nuhlp.recyclerviewwithindex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nuhlp.recyclerviewwithindex.base.BaseViewBindingFragment
import com.nuhlp.recyclerviewwithindex.databinding.FragmentHomeBinding

class HomeFragment :BaseViewBindingFragment<FragmentHomeBinding>()  {



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }
}