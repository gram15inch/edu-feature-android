package com.example.networkhandle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.networkhandle.base.DataBindingActivity
import com.example.networkhandle.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DataBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

}