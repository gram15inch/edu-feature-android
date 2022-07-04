package com.nuhlp.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nuhlp.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    val binding by lazy{ DatabindingUtil.setContentView(this,R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}