package com.nuhlp.googlemapapi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.nuhlp.googlemapapi.databinding.ActivityMapsBinding
import com.nuhlp.googlemapapi.util.PermissionPolicy
import java.util.*


/*
* todo 1
* */


/* map api 학습 일지

  1. gps 요청 -> 권한 필요
  2. 권한 요청 -> ActivityResultLauncher 필요
  3. ARL 요청 -> ActivityResult 필요
  4. AR 요청 -> Contracts, Callback 필요
    4-1. Contracts 학습 ->  Intent 생성, 결과코드 해독 등
    4-2. Callback 학습 -> 결과처리 :: 호출 액티비티가 재시작 되서 작업순서가 사라져도 결과를 처리할 수 있게 결과처리함수를 직접주입하기 위해 사용

 * 7/31
  1. 더 자세한 gps 요청 -> 여러개의 권한 필요 (자세한 gps 단독으로 요쳥 불가 사용자가 정확도를 선택 할 수 있어야함)
  2. 여러개의 권한 요청 -> AL 생성시 여러개의 권한을 담당,처리할 수 있는 Contracts , Callback 필요
    2-1. Contracts -> ActivityResultContracts.RequestMultiplePermissions()
    2-2. Callback  -> (Map<String,Boolean>) -> Unit 타입으로 만든후 권한별 승인여부 확인후 개별처리하는 로직 생성

  1. gps 요청(권한승인) -> 통합위치정보 제공자 필요(FusedLocationProviderClient)
  2. 통제자 요청 ->

* */

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ActivityResultCallback<Map<String,Boolean>>  {
// todo 여기부터
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val LATLNG = LatLng(37.566418,126.977943)
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onActivityResult(permissions: Map<String, Boolean>) = permissions.forEach{
        when{
            it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value ->{ PermissionPolicy.defaultGrant("ACCESS_COARSE_LOCATION")}
            it.key == Manifest.permission.ACCESS_FINE_LOCATION && it.value->{PermissionPolicy.defaultGrant("ACCESS_FINE_LOCATION")}
            else ->{PermissionPolicy.defaultReject(it.key)}
        }
    }


    fun onActivityResult(isGranted: Boolean) {
        if (isGranted)
            PermissionPolicy.defaultGrant()
        else
            PermissionPolicy.defaultReject()
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



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // todo location 위도 경도로 변환하기
            }
        // todo 현재위치로 카메라 포지션 이동
        // todo 여기부터 
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //mapFragment.getMapAsync(test123)

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

    private fun checkPermission(){
        multipleLocationPermissionRequest()


    }

    private fun multipleLocationPermissionRequest(){
        val checkPermission = PermissionPolicy.location.let{array->
            array.all { p->
                ContextCompat.checkSelfPermission(this,p) == PackageManager.PERMISSION_GRANTED }
        }
        val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),this)

        when {
            checkPermission -> { PermissionPolicy.defaultGrant("all grant") }
            /*한번 거절후 다음시작부터 적용*/
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                PermissionPolicy.ration(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionsLauncher.launch(PermissionPolicy.location)
            }
            else -> {
                Log.d("PermissionPolicy","new request!!")
                requestPermissionsLauncher.launch(PermissionPolicy.location)
                // 현재위치로 이동
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
}
