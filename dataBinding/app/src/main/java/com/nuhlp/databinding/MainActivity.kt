package com.nuhlp.databinding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.nuhlp.databinding.databinding.MainActivityBinding
import com.nuhlp.databinding.ui.main.MainFragment
import com.nuhlp.databinding.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding :MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}