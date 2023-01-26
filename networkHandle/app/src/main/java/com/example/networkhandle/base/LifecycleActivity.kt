package com.example.networkhandle.base
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

open class LifecycleActivity : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("lifecycle").d("${this.javaClass.simpleName} : ${object{}.javaClass.enclosingMethod?.name}")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.tag("lifecycle").d("${this.javaClass.simpleName} : ${object{}.javaClass.enclosingMethod?.name}")

    }
    override fun onStart() {
        super.onStart()
        Timber.tag("lifecycle").d("${this.javaClass.simpleName} : ${object{}.javaClass.enclosingMethod?.name}")

    }

    override fun onResume() {
        super.onResume()
        Timber.tag("lifecycle").d("${this.javaClass.simpleName} : ${object{}.javaClass.enclosingMethod?.name}")

    }
    override fun onPause() {
        super.onPause()
        Timber.tag("lifecycle").d("${this.javaClass.simpleName} : ${object{}.javaClass.enclosingMethod?.name}")

    }
    override fun onStop() {
        super.onStop()
        Timber.tag("lifecycle").d("${this.javaClass.simpleName} : ${object{}.javaClass.enclosingMethod?.name}")

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("lifecycle").d("${this.javaClass.simpleName} : ${object{}.javaClass.enclosingMethod?.name}")

    }
}