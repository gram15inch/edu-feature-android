package com.nuhlp.hiltlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nuhlp.hiltlearning.dagger2.BindComponent
import com.nuhlp.hiltlearning.dagger2.ClassA
import com.nuhlp.hiltlearning.databinding.ActivityMainBinding
import dagger.hilt.android.migration.CustomInjection.inject
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding

    @Inject lateinit var a: ClassA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
