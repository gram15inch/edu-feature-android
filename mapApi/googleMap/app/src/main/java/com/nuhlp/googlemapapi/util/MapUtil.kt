package com.nuhlp.googlemapapi.util

import android.location.Location
import androidx.activity.result.ActivityResultCallback
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

interface MapUtil : OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    ActivityResultCallback<Map<String, Boolean>> {


    override fun onMapReady(p0: GoogleMap)
    override fun onMyLocationButtonClick(): Boolean
    override fun onMyLocationClick(p0: Location)
    override fun onActivityResult(result: Map<String, Boolean>?)

}