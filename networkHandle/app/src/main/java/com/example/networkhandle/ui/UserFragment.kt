package com.example.networkhandle.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.networkhandle.R
import com.example.networkhandle.UserViewModel
import com.example.networkhandle.base.DataBindingFragment
import com.example.networkhandle.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment : DataBindingFragment<FragmentUserBinding>(R.layout.fragment_user) {
    private val viewModel: UserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.signUpBtn.setOnClickListener {
            viewModel.resistUser(viewModel.request)
        }
    }
}