package com.nuhlp.customview.nemal

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nuhlp.customview.R

class nomalFragment : Fragment() {

    companion object {
        fun newInstance() = nomalFragment()
    }

    private lateinit var viewModel: NomalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.nomal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NomalViewModel::class.java)
        // TODO: Use the ViewModel
    }

}