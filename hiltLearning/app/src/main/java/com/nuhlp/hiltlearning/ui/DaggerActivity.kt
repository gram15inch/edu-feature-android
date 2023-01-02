package com.nuhlp.hiltlearning.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nuhlp.hiltlearning.HiltApplication
import com.nuhlp.hiltlearning.dagger2.ClassA
import com.nuhlp.hiltlearning.databinding.ActivityDaggerBinding

import javax.inject.Inject

class DaggerActivity : AppCompatActivity() {
    lateinit var binding : ActivityDaggerBinding
    @Inject lateinit var a : ClassA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaggerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as HiltApplication).component.createMainSubComponent().inject(this)

        binding.btnClick.setOnClickListener {
            a.executeA()
        }
    }
}