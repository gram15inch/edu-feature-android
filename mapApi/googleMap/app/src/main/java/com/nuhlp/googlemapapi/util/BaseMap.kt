package com.nuhlp.googlemapapi.util

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.nuhlp.googlemapapi.R

abstract class BaseMap: AppCompatActivity(),MapUtil {
    abstract val markerResourceId :Int
    abstract val mapFragmentId:Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateAfter(savedInstanceState)
    }
    abstract fun onCreateAfter(savedInstanceState: Bundle?)














    /* callback */
    override fun onMapReady(p0: GoogleMap) {

    }
    override fun onMyLocationButtonClick(): Boolean {
     return false
    }
    override fun onMyLocationClick(p0: Location) {

    }
    override fun onActivityResult(result: Map<String, Boolean>?) {

    }

}