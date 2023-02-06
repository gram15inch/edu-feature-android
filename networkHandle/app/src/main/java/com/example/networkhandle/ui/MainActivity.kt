package com.example.networkhandle.ui

import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.networkhandle.R
import com.example.networkhandle.base.DataBindingActivity
import com.example.networkhandle.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : DataBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        //todo 삭제
        Firebase.storage
            .reference.child("react/tn_react1.jpg")
            .downloadUrl
            .addOnSuccessListener {
                Timber.tag("firebase").d("uri: ${it}")
            }.addOnFailureListener {
                Timber.tag("firebase").d("fail")
            }
    }
}