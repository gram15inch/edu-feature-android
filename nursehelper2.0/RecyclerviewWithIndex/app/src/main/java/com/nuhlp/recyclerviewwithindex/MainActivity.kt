package com.nuhlp.recyclerviewwithindex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nuhlp.recyclerviewwithindex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}