package com.nuhlp.googlemapapi.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.nuhlp.googlemapapi.util.Constants.LATLNG_DONGBAEK
import java.util.*

abstract class BaseMapActivity :AppCompatActivity(),MapUtil {


    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var locationCallback: LocationCallback
    private var locationRequest: LocationRequest
    private var isOnGPS :Boolean

    init {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let{
                    for((i,location) in it.locations.withIndex()){
                        Log.d("MapsActivity","$i ${location.latitude}, ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        isOnGPS = false
    }

    /* abstract */
    abstract val markerResourceId : Int
    abstract val mapFragmentId : Int
    abstract fun updateLatLng(latLng: LatLng)
    abstract fun onCreateAfter(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multipleLocationPermissionRequest()
        onCreateAfter(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(mapFragmentId) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /* Map Util CallBack */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setCamera(LATLNG_DONGBAEK)

        locationSettingRequest()

        showGps(mMap)

        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)

    }
    override fun onActivityResult(permissions: Map<String, Boolean>) = permissions.forEach{
        when{
            it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value ->{PermissionPolicy.defaultGrant("ACCESS_COARSE_LOCATION")
                showGps(mMap)
            }
            it.key == Manifest.permission.ACCESS_FINE_LOCATION && it.value->{PermissionPolicy.defaultGrant("ACCESS_FINE_LOCATION")}
            else ->{PermissionPolicy.defaultReject(it.key)}
        }
    }
    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }
    override fun onMyLocationButtonClick(): Boolean {

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        if(!isOnGPS) {
            isOnGPS = true
            Toast.makeText(this, "MyLocation button clicked : $isOnGPS", Toast.LENGTH_SHORT).show()
            updateLocation()
        }
        else
        {
            isOnGPS= false
            Toast.makeText(this, "MyLocation button clicked : $isOnGPS", Toast.LENGTH_SHORT).show()
            stopLocation()
        }
        return false
    }


    /* Activity Util */
    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }
    private fun stopLocation() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        mMap.clear()
    }
    private fun setLastLocation(lastLocation: Location) {
        mMap.clear()
        LatLng(lastLocation.latitude,lastLocation.longitude).let{
            setCamera(it)
            setMarker(it)
            updateLatLng(it)
        }
    }
    private fun showGps(mMap:GoogleMap){
        val checkP= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if(checkP == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }

    fun setMarker(latLng: LatLng) {
        val bitmapDrawable = bitmapDescriptorFromVector(this, markerResourceId)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(latLng)
            .icon(discriptor)
        markerOptions.setAddress()
        /*.title("marker in Seoul City Hall")
            .snippet("37.566418,126.977943")*/
        mMap.addMarker(markerOptions)
    }
    fun setCamera(latLng: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(20.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.moveCamera(cameraUpdate)
    }


    /* Component Util */
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
    private fun MarkerOptions.setAddress(){
        try {
            val geo =
                Geocoder(this@BaseMapActivity, Locale.getDefault())
            val addresses: List<Address> = geo.getFromLocation(LATLNG_DONGBAEK.latitude,LATLNG_DONGBAEK.longitude, 1)
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


    /* permission */
    private fun multipleLocationPermissionRequest(){
        val checkPermission = PermissionPolicy.location.let{array->
            array.all { p->
                ContextCompat.checkSelfPermission(this,p) == PackageManager.PERMISSION_GRANTED }
        }
        val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),this)

        when {
            checkPermission -> { PermissionPolicy.defaultGrant("all grant")
            }
            /*한번 거절후 다음시작부터 적용*/
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                PermissionPolicy.ration(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionsLauncher.launch(PermissionPolicy.location)
            }
            else -> {
                Log.d("PermissionPolicy","new request!!")
                requestPermissionsLauncher.launch(PermissionPolicy.location)
            }
        }
    }
    private fun locationSettingRequest(){

        val REQUEST_CHECK_SETTINGS = 0x1
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)

                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    Log.d("MapsActivity","========= catch")

                }
            }
        }

    }


    // ***** 사용안함 *****
    private fun withCurrentLatLng(callback:(LatLng)->Unit) {
        val checkP = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (checkP == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                callback(LatLng(it.latitude, it.longitude))
                Log.d("MapsActivity","${it.latitude} ${it.longitude}")
            }
        }
    }
    private fun soloLocationPermissionRequest(){
        val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}
        when {
            checkPermission == PackageManager.PERMISSION_GRANTED -> {
                PermissionPolicy.defaultGrant("")
            }
            /*한번 거절후 다음시작부터 적용*/
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                PermissionPolicy.ration(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }
    fun moveCameraLauncher(map:GoogleMap,latLng: LatLng){
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.moveCamera(cameraUpdate)
    }
}