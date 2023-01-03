package com.nuhlp.hiltlearning.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.nuhlp.hiltlearning.databinding.ActivityHiltBinding
import com.nuhlp.hiltlearning.hilt.ClassA
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHiltBinding
    private val viewModel: MyHiltViewModel by viewModels()
    @Inject lateinit var a : ClassA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiltBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClick.setOnClickListener {
            a.executeA()
            viewModel.executeViewModel()
        }
    }
}