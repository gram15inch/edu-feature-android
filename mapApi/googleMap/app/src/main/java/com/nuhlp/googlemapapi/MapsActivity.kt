package com.nuhlp.googlemapapi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.nuhlp.googlemapapi.databinding.ActivityMapsBinding
import java.util.*



/*
* todo 1
* */



class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ActivityResultCallback<Boolean> {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val LATLNG = LatLng(37.566418,126.977943)
    val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
    val test1 : (Boolean)-> Unit = { b: Boolean ->  }

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(), this)

    // todo result activity 문서 부터 학습

    private fun rejectPermission() {
        TODO("Not yet implemented")
    }

    private fun grantPermission() {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(result: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(17.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.moveCamera(cameraUpdate)

        val bitmapDrawable = bitmapDescriptorFromVector(this,R.drawable.marker)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(LATLNG)
            .icon(discriptor)
        markerOptions.setAddress()
        /*.title("marker in Seoul City Hall")
            .snippet("37.566418,126.977943")*/
        mMap.addMarker(markerOptions)
        // TODO MAP 공시문서 학습


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.getMapAsync(test123)


        when {
            checkPermission == PackageManager.PERMISSION_GRANTED -> {
                grantPermission()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                ration(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }

    }


    private fun ration(permission: String) {

    }

    val test123 = OnMapReadyCallback{

    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private  fun MarkerOptions.setAddress(){
        try {// todo 주소 클래스 분석
            val geo =
                Geocoder(this@MapsActivity, Locale.getDefault())
            val addresses: List<Address> = geo.getFromLocation(LATLNG.latitude,LATLNG.longitude, 1)
            if (addresses.isEmpty()) {
                title("Waiting for Location")
            } else {
                if (addresses.size > 0) {
                    addresses[0].apply{
                        title(this.getAddressLine(0))
                            Log.d("MapsActivity","0: ${this.getAddressLine(0)} \n")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // getFromLocation() may sometimes fail
        }
    }
}
fun asd(){

}