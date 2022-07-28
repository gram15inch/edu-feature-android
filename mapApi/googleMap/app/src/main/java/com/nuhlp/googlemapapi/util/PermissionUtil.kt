package com.nuhlp.googlemapapi.util

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

abstract class PermissionUtil(ca:AppCompatActivity,permission: String)  {
    init {
        val checkPermission = ContextCompat.checkSelfPermission(ca, permission)
        val requestPermissionLauncher = ca.registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted)
                grantPermission()
             else
                rejectPermission()
        }

        when {
             checkPermission == PackageManager.PERMISSION_GRANTED -> {
                grantPermission()
            }
            ca.shouldShowRequestPermissionRationale(permission) -> {
                ration(permission)
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    abstract fun rejectPermission()
    abstract fun grantPermission()
    private fun ration(permission: String) {
     Log.d("PermissionUtil","call ration!")
    //todo ca.showInContextUI(...)
    }

}